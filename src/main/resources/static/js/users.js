var unitId = 0;

function applyButtons()
{
    if (unitId == 0) {
        document.getElementById("user-edit").disabled = true;
        da = document.getElementById("user-activate");
        if (da != null) da.disabled = true;
        dd = document.getElementById("user-deactivate");
        if (dd != null) dd.disabled = true;
    }
}

function selectUser(id)
{
    if (unitId == 0) {
        document.getElementById("user-edit").disabled = false;
        da = document.getElementById("user-activate");
        if (da != null) da.disabled = false;
        dd = document.getElementById("user-deactivate");
        if (dd != null) dd.disabled = false;
    }
    unitId = id;
}

function selectUnit(id)
{
    e = document.getElementById("user-unit");
    for (i = 0; i < e.options.length; i++) {
        if (id == e.options[i].value) {
            e.selectedIndex = i;
            break;
        }
    }
}

function createUser()
{
    window.location.href = "/Users?action=create";
}

function updateUser()
{
    window.location.href = "/Users?action=update&id=" + unitId;
}

function activateUser()
{
    window.location.href = "/Users?action=activate&id=" + unitId;
}

function deactivateUser()
{
    window.location.href = "/Users?action=deactivate&id=" + unitId;
}

function deactivateUserList()
{
    window.location.href = "/Users?deactivated";
}
