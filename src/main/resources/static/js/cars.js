var modelId = null;
var carId = null;

function modelList()
{
    window.location.href = "/Cars?action=modelList";
}

function carList()
{
    window.location.href = "/Cars";
}

function selectModel(id)
{
    modelId = id;
    applyModelButtons();
}

function selectCar(id)
{
    carId = id;
    applyCarButtons();
}

function applyModelButtons()
{
    document.getElementById("modelUpdateButton").disabled = modelId == null;
    document.getElementById("modelRemoveButton").disabled = modelId == null;
}

function applyCarButtons()
{
    document.getElementById("carUpdateButton").disabled = carId == null;
}

function createModel()
{
    document.getElementById("model-id").value = "0";
    document.getElementById("model-name").value = "";
    document.getElementById("editmodel").style.display = "block";
    document.getElementById("editbar").style.width = getClientWidth();
    document.getElementById("editbar").style.height = getClientHeight();
}

function createCar()
{
    document.getElementById("car-id").value = "0";
    document.getElementById("car-no").value = "";
    document.getElementById("car-name").value = "";
    document.getElementById("car-model").selectedIndex = 0;
    document.getElementById("car-enabled").checked = true;
    document.getElementById("editcar").style.display = "block";
    document.getElementById("editbar").style.width = getClientWidth();
    document.getElementById("editbar").style.height = getClientHeight();
}

function updateModel()
{
    if (modelId != null) {
        document.getElementById("model-id").value = modelId;
        document.getElementById("model-name").value = document.getElementById("model-name-" + modelId).innerHTML;
        document.getElementById("editmodel").style.display = "block";
        document.getElementById("editbar").style.width = getClientWidth();
        document.getElementById("editbar").style.height = getClientHeight();
    }
}

function updateCar()
{
    if (carId != null) {
        document.getElementById("car-id").value = carId;
        document.getElementById("car-no").value = document.getElementById("car-no-" + carId).innerHTML;
        document.getElementById("car-name").value = document.getElementById("car-name-" + carId).innerHTML;
        var model = document.getElementById("car-model-" + carId).innerHTML;
        var list = document.getElementById("car-model").options;
        if (list.length > 0)
            for (i = 0; i < list.length; i++)
                if (list[i].innerHTML == model) {
                    document.getElementById("car-model").selectedIndex = i;
                    break;
                }
        document.getElementById("car-enabled").checked = document.getElementById("car-enabled-" + carId).checked;
        document.getElementById("editcar").style.display = "block";
        document.getElementById("editbar").style.width = getClientWidth();
        document.getElementById("editbar").style.height = getClientHeight();
    }
}

function hideEditModelBox()
{
    document.getElementById("editmodel").style.display = "none";
}

function removeModel()
{
    if (modelId != null && confirm("Удалить модель транспортного средства?"))
        window.location.href = "/Cars?action=removeModel&model-id=" + modelId;
}

function hideEditCarBox()
{
    document.getElementById("editcar").style.display = "none";
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