package com.blocadminmicromodern.webservice.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.blocadminmicromodern.webservice.dto.BudgetDTO;
import com.blocadminmicromodern.webservice.dto.ExpenseDTO;
import com.blocadminmicromodern.webservice.dto.HouseholdDTO;
import com.blocadminmicromodern.webservice.dto.RequestDTO;
import com.blocadminmicromodern.webservice.dto.UserDTO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFExporter {

	public enum EXPORT_TYPE {
		USERS, BUDGETS, REQUESTS, HOUSEHOLDS, EXPENSES;
	}

	public void export(HttpServletResponse response, String listType, EXPORT_TYPE exportType, List<UserDTO> users,
			List<BudgetDTO> budgets, List<ExpenseDTO> expenses, List<HouseholdDTO> houses, List<RequestDTO> requests)
			throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("List of ".concat(listType), font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = null;
		switch (exportType) {
		case USERS:
			table = writeUserTableData(users);
			break;
		case BUDGETS:
			table = writeBudgetsTableData(budgets);
			break;
		case EXPENSES:
			table = writeExpensesTableData(expenses);
			break;
		case REQUESTS:
			table = writeRequestsTableData(requests);
			break;
		case HOUSEHOLDS:
			table = writeHouseholdsTableData(houses);
			break;
		default:
		}

		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		document.add(table);

		document.close();
	}

	private void writeTableHeader(PdfPTable table, LinkedHashSet<String> headers) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		for (String tableHeader : headers) {
			cell.setPhrase(new Phrase(tableHeader, font));
			table.addCell(cell);
		}
	}

	private PdfPTable writeUserTableData(List<UserDTO> users) {
		PdfPTable table = new PdfPTable(5);
		LinkedHashSet<String> headers = new LinkedHashSet<String>();
		headers.add("Name");
		headers.add("Username");
		headers.add("Building no.");
		headers.add("Apartment no.");
		headers.add("Type");
		writeTableHeader(table, headers);
		for (UserDTO user : users) {
			table.addCell(user.getFullName());
			table.addCell(user.getUsername());
			table.addCell(String.valueOf(user.getBuildingNr()));
			table.addCell(String.valueOf(user.getAppartmentNr()));
			table.addCell(user.getUserTypeEnum().getName());
		}
		table.setWidths(new float[] { 3.5f, 3.5f, 1.5f, 1.5f, 3.5f });
		return table;
	}

	private PdfPTable writeBudgetsTableData(List<BudgetDTO> budgets) {
		PdfPTable table = new PdfPTable(3);
		LinkedHashSet<String> headers = new LinkedHashSet<String>();
		headers.add("Type");
		headers.add("Total Sum");
		headers.add("Left Sum");
		writeTableHeader(table, headers);
		for (BudgetDTO budget : budgets) {
			table.addCell(budget.getBudgetTypeEnum().getName());
			table.addCell(String.valueOf(budget.getTotalSum()));
			table.addCell(String.valueOf(budget.getLeftoverSum()));
		}
		table.setWidths(new float[] { 3.5f, 3.5f, 3.5f });
		return table;
	}

	private PdfPTable writeExpensesTableData(List<ExpenseDTO> expenses) {
		PdfPTable table = new PdfPTable(6);
		LinkedHashSet<String> headers = new LinkedHashSet<String>();
		headers.add("Type");
		headers.add("Due date");
		headers.add("Total Sum");
		headers.add("Left Sum");
		headers.add("Address");
		headers.add("Payed");
		writeTableHeader(table, headers);
		for (ExpenseDTO expense : expenses) {
			table.addCell(expense.getExpenseTypeEnum().getName());
			table.addCell(expense.getFormattedDueDate());
			table.addCell(String.valueOf(expense.getTotalSum()));
			table.addCell(String.valueOf(expense.getLeftoverSum()));
			table.addCell(expense.getExpenseAddressesFormatted());
			table.addCell(expense.isPayedInFull() ? "Yes" : "No");
		}
		table.setWidths(new float[] { 3.0f, 2.5f, 1.5f, 1.5f, 3.5f, 1.5f });
		return table;
	}

	private PdfPTable writeRequestsTableData(List<RequestDTO> requests) {
		PdfPTable table = new PdfPTable(4);
		LinkedHashSet<String> headers = new LinkedHashSet<String>();
		headers.add("Type");
		headers.add("Due date");
		headers.add("Address");
		headers.add("Resolved");
		writeTableHeader(table, headers);
		for (RequestDTO request : requests) {
			table.addCell(request.getRequestTypeEnum().getName());
			table.addCell(request.getFormattedDueDate());
			table.addCell(request.getHouseholdAddress());
			table.addCell(request.isResolved() ? "Yes" : "No");
		}
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f });
		return table;
	}

	private PdfPTable writeHouseholdsTableData(List<HouseholdDTO> households) {
		PdfPTable table = new PdfPTable(6);
		LinkedHashSet<String> headers = new LinkedHashSet<String>();
		headers.add("Adress");
		headers.add("Owner");
		headers.add("Rooms");
		headers.add("Occupants");
		headers.add("Capacity");
		headers.add("Debt");
		writeTableHeader(table, headers);
		for (HouseholdDTO household : households) {
			table.addCell(household.getAddress());
			table.addCell(household.getOwnerName());
			table.addCell(String.valueOf(household.getRoomsNr()));
			table.addCell(String.valueOf(household.getNrCurrentOccupants()));
			table.addCell(String.valueOf(household.getTotalCapacity()));
			table.addCell(String.valueOf(household.getTotalDebt()));
		}
		table.setWidths(new float[] { 3.0f, 3.5f, 1.5f, 1.5f, 1.5f, 1.5f });
		return table;
	}
}
