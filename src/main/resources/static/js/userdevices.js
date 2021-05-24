function selectCar(id)
{
    document.getElementById("device-id").value = id;
    document.getElementById("editmodel").style.display = "block";
    document.getElementById("selectbar").style.width = getClientWidth();
    document.getElementById("selectbar").style.height = getClientHeight();
}

function raiseDevice(id)
{
    window.location.href="/Devices?action=raiseDevice&id=" + id;
}

function hideSelectCarBox()
{
    document.getElementById("editmodel").style.display = "none";
}

function getClientWidth()
{
    if (typeof(window.innerWidth) == 'number') return window.innerWidth + "px";
    else if (document.documentElement && document.documentElement.clientWidth) return document.documentElement.clientWidth + "px";
    else if (document.body && document.body.clientWidth) return document.body.clientWidth + "px";
    else return "100%";
}

function getClientHeight()
{
    if (typeof(window.innerHeight) == 'number') return window.innerHeight + "px";
    else if (document.documentElement && document.documentElement.clientHeight) return document.documentElement.clientHeight + "px";
    else if (document.body && document.body.clientHeight) return document.body.clientHeight + "px";
    else return "100%";
}