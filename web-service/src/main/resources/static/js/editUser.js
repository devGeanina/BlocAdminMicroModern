$('.editUser').click(function() { //example of passing the information to the edit modal through Javascript
	//clone dialog and remove ids to ensure uniqueness
	var $modal = $('#editUserModal').clone().removeAttr('id');

	//apply custom values where needed
	var $btn = $(this);
	var rowId = $btn.attr('data-id');
	var firstname = $btn.attr('data-firstname');
	var lastname = $btn.attr('data-lastname');


	$modal.find('[data-value="firstname"]').text(firstname);
	$modal.find('[data-value="lastname"]').text(lastname);
	$modal.find('[data-value="appartmentNr"]').text(appartmentNr);
	$modal.find('[data-value="buildingNr"]').text(buildingNr);
	$modal.find('[data-value="details"]').text(details);
	$modal.find('[name="id"]').val($btn.attr('data-id'));
	$modal.find('form').attr('action').replace('_id_', rowId);
	$modal.find('button[type="submit"]').attr('href', $modal.find('button[type="submit"]').attr('href').replace('_id_', rowId));

	//show dialog
	$modal.modal();
});