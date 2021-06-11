package com.blocadminmicromodern.webservice.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.blocadminmicromodern.webservice.dto.BudgetDTO;
import com.blocadminmicromodern.webservice.dto.ExpenseDTO;
import com.blocadminmicromodern.webservice.dto.HouseholdDTO;
import com.blocadminmicromodern.webservice.dto.RequestDTO;
import com.blocadminmicromodern.webservice.dto.UserDTO;
import com.blocadminmicromodern.webservice.service.HomeService;
import com.blocadminmicromodern.webservice.utils.PDFExporter;
import com.blocadminmicromodern.webservice.utils.PDFExporter.EXPORT_TYPE;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/")
@SessionAttributes({ "budgetSummary", "expenseSummary", "houseSummary", "requestSummary" })
public class GUIController {

	private static final Logger LOGGER = LogManager.getLogger(GUIController.class);
	private final RestTemplate restTemplate;
	private final HomeService homeService;

	private final String serviceHostUsers = "user-service"; // Eureka handles the actual hosts
	private final String serviceHostBudgets = "budget-service";
	private final String serviceHostOperations = "operation-service";

	public GUIController(RestTemplate restTemplate, HomeService homeService) {
		this.restTemplate = restTemplate;
		this.homeService = homeService;
	}

	@RequestMapping("/home")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String home(Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses/debt", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("housesDebt", housesDTOs.getBody());
		getSummaryInfo(model);
		return "home";
	}

	@RequestMapping("/")
	public String index(Model model) {
		return "redirect:/home";
	}

	@RequestMapping("/login")
	public String login() {
		return "redirect:/login";
	}

	@RequestMapping("/logout")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String logout(Model model) {
		return "/login";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@GetMapping("/users")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String getUsers(Model model) {
		ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange("http://" + serviceHostUsers + "/users",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
				});
		model.addAttribute("users", usersDTOs.getBody());
		model.addAttribute("user", new UserDTO());
		return "viewUsers";
	}

	@GetMapping("/users/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void findUser(@PathVariable UUID uuid, Model model) {
		ResponseEntity<UserDTO> userDTO = restTemplate.exchange("http://" + serviceHostUsers + "/users/" + uuid,
				HttpMethod.GET, null, new ParameterizedTypeReference<UserDTO>() {
				});
		model.addAttribute("user", userDTO);
	}

	@PostMapping("/users/save")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String submitUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostUsers + "/users/save";
		userDTO.setUserType(userDTO.getUserTypeEnum().getType());
		model.addAttribute("user", userDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<UserDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(userDTO);
			ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<UserDTO>>() {
					});

			model.addAttribute("users", usersDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new user: " + e.getMessage());
		}
		return "redirect:/users";
	}

	@GetMapping("/users/delete/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String deleteUser(@PathVariable UUID uuid, Model model) {
		ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange(
				"http://" + serviceHostUsers + "/users/delete/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserDTO>>() {
				});
		model.addAttribute("users", usersDTOs.getBody());
		return "redirect:/users";
	}

	@RequestMapping("/users/export")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void exportUsersToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange("http://" + serviceHostUsers + "/users",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "users", EXPORT_TYPE.USERS, usersDTOs.getBody(), null, null, null, null);
	}

	@GetMapping("/budgets")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String getBudgets(Model model) {
		ResponseEntity<List<BudgetDTO>> budgetsDTOs = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<BudgetDTO>>() {
				});
		model.addAttribute("budgets", budgetsDTOs.getBody());
		model.addAttribute("budget", new BudgetDTO());
		return "viewBudgets";
	}

	@GetMapping("/budgets/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void findBudget(@PathVariable UUID uuid, Model model) {
		ResponseEntity<BudgetDTO> budgetDTO = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets/" + uuid,
				HttpMethod.GET, null, new ParameterizedTypeReference<BudgetDTO>() {
				});
		model.addAttribute("budget", budgetDTO);
	}

	@PostMapping("/budgets/save")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String submitBudget(@Valid @ModelAttribute("budget") BudgetDTO budgetDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostBudgets + "/budgets/save";
		budgetDTO.setBudgetType(budgetDTO.getBudgetTypeEnum().getType());
		model.addAttribute("budget", budgetDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<BudgetDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(budgetDTO);
			ResponseEntity<List<BudgetDTO>> budgetDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<BudgetDTO>>() {
					});

			model.addAttribute("budgets", budgetDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new budget: " + e.getMessage());
		}
		return "redirect:/budgets";
	}

	@GetMapping("/budgets/delete/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String deleteBudget(@PathVariable UUID uuid, Model model) {
		ResponseEntity<List<BudgetDTO>> budgetDTOs = restTemplate.exchange(
				"http://" + serviceHostBudgets + "/budgets/delete/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BudgetDTO>>() {
				});
		model.addAttribute("budgets", budgetDTOs.getBody());
		return "redirect:/budgets";
	}

	@RequestMapping("/budgets/export")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void exportBudgetsToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=budgets_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<BudgetDTO>> budgetDTOs = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<BudgetDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "budgets", EXPORT_TYPE.BUDGETS, null, budgetDTOs.getBody(), null, null, null);
	}

	@GetMapping("/expenses")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String getExpenses(Model model) {
		ResponseEntity<List<ExpenseDTO>> expensesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});
		model.addAttribute("expenses", expensesDTOs.getBody());
		ResponseEntity<List<HouseholdDTO>> householdsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", householdsDTOs.getBody());
		model.addAttribute("expense", new ExpenseDTO());

		return "viewExpenses";
	}

	@GetMapping("/expenses/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void findExpense(@PathVariable UUID uuid, Model model) {
		ResponseEntity<ExpenseDTO> expenseDTO = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<ExpenseDTO>() {
				});
		model.addAttribute("expense", expenseDTO);
	}

	@PostMapping("/expenses/save")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String submitExpense(@Valid @ModelAttribute("expense") ExpenseDTO expenseDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostOperations + "/expenses/save";
		expenseDTO.setExpenseType(expenseDTO.getExpenseTypeEnum().getType());
		model.addAttribute("expense", expenseDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<ExpenseDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(expenseDTO);
			ResponseEntity<List<ExpenseDTO>> expenseDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<ExpenseDTO>>() {
					});

			model.addAttribute("expenses", expenseDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new expense: " + e.getMessage());
		}
		return "redirect:/expenses";
	}

	@GetMapping("/expenses/delete/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String deleteExpense(@PathVariable UUID uuid, Model model) {
		ResponseEntity<List<ExpenseDTO>> expenseDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses/delete/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});
		model.addAttribute("expenses", expenseDTOs.getBody());
		return "redirect:/expenses";
	}

	@RequestMapping("/expenses/export")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void exportExpensesToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=expenses_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<ExpenseDTO>> expensesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "expenses", EXPORT_TYPE.EXPENSES, null, null, expensesDTOs.getBody(), null, null);
	}

	@GetMapping("/requests")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String getRequests(Model model) {
		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});
		ResponseEntity<List<HouseholdDTO>> householdsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", householdsDTOs.getBody());
		model.addAttribute("requests", requestsDTOs.getBody());
		model.addAttribute("request", new RequestDTO());

		return "viewRequests";
	}

	@GetMapping("/requests/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void findRequest(@PathVariable UUID uuid, Model model) {
		ResponseEntity<RequestDTO> requestDTO = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<RequestDTO>() {
				});
		model.addAttribute("request", requestDTO);
	}

	@PostMapping("/requests/save")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String submitRequest(@Valid @ModelAttribute("request") RequestDTO requestDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostOperations + "/requests/save";
		requestDTO.setRequestType(requestDTO.getRequestTypeEnum().getType());
		model.addAttribute("request", requestDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<RequestDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(requestDTO);
			ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<RequestDTO>>() {
					});

			model.addAttribute("requests", requestsDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new request: " + e.getMessage());
		}
		return "redirect:/requests";
	}

	@GetMapping("/requests/delete/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String deleteRequest(@PathVariable UUID uuid, Model model) {
		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests/delete/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});
		model.addAttribute("requests", requestsDTOs.getBody());
		return "redirect:/requests";
	}

	@RequestMapping("/requests/export")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void exportRequestsToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=requests_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "requests", EXPORT_TYPE.REQUESTS, null, null, null, null, requestsDTOs.getBody());
	}

	@GetMapping("/houses")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String getHouseholds(Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", housesDTOs.getBody());
		model.addAttribute("household", new HouseholdDTO());

		return "viewHouses";
	}

	@GetMapping("/houses/debt")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public String getHouseholdsWithDebt(Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("housesDebt", housesDTOs.getBody());
		return "home";
	}

	@GetMapping("/houses/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void findHousehold(@PathVariable UUID uuid, Model model) {
		ResponseEntity<HouseholdDTO> houseDTO = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<HouseholdDTO>() {
				});
		model.addAttribute("household", houseDTO);
	}

	@PostMapping("/houses/save")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String submitHouse(@Valid @ModelAttribute("household") HouseholdDTO houseDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostOperations + "/houses/save";
		model.addAttribute("household", houseDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<HouseholdDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(houseDTO);
			ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<HouseholdDTO>>() {
					});

			model.addAttribute("houses", housesDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new household: " + e.getMessage());
		}
		return "redirect:/houses";
	}

	@GetMapping("/houses/delete/{uuid}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	String deleteHousehold(@PathVariable UUID uuid, Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses/delete/" + uuid, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", housesDTOs.getBody());
		return "redirect:/houses";
	}

	@RequestMapping("/houses/export")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void exportHousesToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=houses_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "households", EXPORT_TYPE.HOUSEHOLDS, null, null, null, housesDTOs.getBody(), null);
	}

	/**
	 * Create summary information for the dialogs and persist across requests, in
	 * session.
	 * 
	 * @param model
	 */
	private void getSummaryInfo(Model model) {
		// create for budget summary
		ResponseEntity<List<BudgetDTO>> budgetsDTOs = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<BudgetDTO>>() {
				});
		model.addAttribute("budgetSummary", homeService.createBudgetSummary(budgetsDTOs.getBody()));

		// create for expense summary
		ResponseEntity<List<ExpenseDTO>> expensesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});
		model.addAttribute("expenseSummary", homeService.creatExpenseSummary(expensesDTOs.getBody()));

		// create for request summary
		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});
		model.addAttribute("requestSummary", homeService.createRequestSummary(requestsDTOs.getBody()));

		// create for house summary
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houseSummary", homeService.createHouseholdSummary(housesDTOs.getBody()));
	}
}
