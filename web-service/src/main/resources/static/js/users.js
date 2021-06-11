
$.getJSON("/users", function (users) {
    var $tableBody = $("#users tbody");
    users.forEach(function (item) {
        var $line = $("<tr>");
        $line.append( $("<td>").text(item.firstName) );
        $line.append( $("<td>").text(item.lastName) );
        $line.append( $("<td>").text(item.fullName) );
        $line.append( $("<td>").text(item.userType) );
        $line.append( $("<td>").text(item.buildingNr) );
        $line.append( $("<td>").text(item.details) );
        $line.append( $("<td>").text(item.appartmentNr) );
        $tableBody.append($line);
    })
});