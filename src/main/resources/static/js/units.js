var unitId = 0;

function applyButtons()
{
    if (unitId == 0) {
        document.getElementById("unit-edit").disabled = true;
        document.getElementById("unit-remove").disabled = true;
    }
}

function selectUnit(id)
{
    if (unitId == 0) {
        document.getElementById("unit-edit").disabled = false;
        document.getElementById("unit-remove").disabled = false;
    }
    unitId = id;
}

function createUnit()
{
    window.location.href = "/?action=create";
}

function updateUnit()
{
    window.location.href = "/?action=update&id=" + unitId;
}

function removeUnit()
{
    msg = "Удалить подразделение?\n" +
        "Внимание! При удалении подразделения,\n" +
        "удаляется база данных этого подразделения.";
    if (confirm(msg)) {
        window.location.href = "/?action=remove&id=" + unitId;
    }
}