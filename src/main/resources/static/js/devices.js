var unitId = 0;

function applyButtons()
{
    if (unitId == 0) {
        document.getElementById("device-edit").disabled = true;
        da = document.getElementById("device-activate");
        if (da != null) da.disabled = true;
        dd = document.getElementById("device-deactivate");
        if (dd != null) dd.disabled = true;
        document.getElementById("device-report1").disabled = true;
        document.getElementById("device-report2").disabled = true;
    }
}

function selectDevice(id)
{
    if (unitId == 0) {
        document.getElementById("device-edit").disabled = false;
        da = document.getElementById("device-activate");
        if (da != null) da.disabled = false;
        dd = document.getElementById("device-deactivate");
        if (dd != null) dd.disabled = false;
    }
    unitId = id;
}

function selectType(id)
{
    document.getElementById("device-type").selectedIndex = id;
}

function selectUnit(id)
{
    e = document.getElementById("device-unit");
    for (i = 0; i < e.options.length; i++) {
        if (id == e.options[i].value) {
            e.selectedIndex = i;
            break;
        }
    }
}

function createDevice()
{
    window.location.href = "/Devices?action=create";
}

function updateDevice()
{
    window.location.href = "/Devices?action=update&id=" + unitId;
}

function activateDevice()
{
    window.location.href = "/Devices?action=activate&id=" + unitId;
}

function deactivateDevice()
{
    window.location.href = "/Devices?action=deactivate&id=" + unitId;
}

function deactivateDeviceList()
{
    window.location.href = "/Devices?deactivated";
}

function reportDevice1()
{

}

function reportDevice2()
{

}