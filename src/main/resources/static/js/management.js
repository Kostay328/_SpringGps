window.ining = window.ining || {};
ining.gps = ining.gps || {
    
    connectedFunc: null,
    updateFunc: null,
    
    clear: function()
    {
        document.getElementById("connection-bar").style.display = "block";
        document.getElementById("connection").style.display = "none";
        document.getElementById("disconnection-bar").style.display = "none";
        document.getElementById("text").disabled = true;
        document.getElementById("message").disabled = true;
        document.getElementById("send").disabled = true;
        document.getElementById("text").value = "...";
        if (ining.gps.connectedFunc != null) clearInterval(ining.gps.connectedFunc);
        if (ining.gps.updateFunc != null) clearInterval(ining.gps.updateFunc);
        ining.gps.connectedFunc = null;
        ining.gps.updateFunc = null;
    },
    
    connect: function()
    {
        var device = document.getElementById("device").value;
        var car = document.getElementById("car").value;
        var url = "/Management?";
        if (device != 0) url += "device=" + device;
        else if (car != 0) url += "device=" + device;
        else {
            alert("Не выбран прибор!");
            return;
        }
        var req = new this.interaction(url, this.connectProcess);
        req.doGet();
    },
    
    connectProcess: function(xml)
    {
        if (xml == null) {
            alert("Не удалось соединится с прибором");
            ining.gps.clear();
            return;
        } else {
            var c = xml.documentElement;
            var err = c.getAttribute("error");
            if (err != null) {
                alert(err);
                ining.gps.clear();
                return;
            } else {
                document.getElementById("connection-bar").style.display = "none";
                document.getElementById("connection").style.display = "block";
                ining.gps.connectedFunc = setInterval("ining.gps.connected()", 1000);
            }
        }
    },
    
    connected: function()
    {
        var url = "/Management?connected";
        var req = new this.interaction(url, this.connectedProcess);
        req.doGet();
    },
    
    connectedProcess: function(xml)
    {
        if (xml == null) {
            alert("Ошибка! connectedProcess - response no xml");
            ining.gps.clear();
            return;
        } else {
            var c = xml.documentElement;
            var err = c.getAttribute("error");
            if (err != null) {
                alert(err);
                ining.gps.clear();
                return;
            } else {
                var r = c.getAttribute("result");
                if (r != null && r == "true") {
                    document.getElementById("connection").style.display = "none";
                    document.getElementById("disconnection-bar").style.display = "block";
                    document.getElementById("text").disabled = false;
                    document.getElementById("message").disabled = false;
                    document.getElementById("send").disabled = false;
                    clearInterval(ining.gps.connectedFunc);
                    ining.gps.connectedFunc = null;
                    ining.gps.updateFunc = setInterval("ining.gps.update()", 1000);
                } 
            }
        }
    },
    
    update: function()
    {
        var url = "/Management?update";
        var req = new this.interaction(url, this.updateProcess);
        req.doGet();
    },
    
    updateProcess: function(xml)
    {
        if (xml == null) {
            alert("Ошибка! updateProcess - response no xml");
            ining.gps.clear();
            return;
        } else {
            var c = xml.documentElement;
            var err = c.getAttribute("error");
            if (err != null) {
                alert(err);
                ining.gps.clear();
            } else {
                var t = document.getElementById("text");
                var m = c.getAttribute("result")
                if (m != "") {
                    t.value += m;
                    t.scrollTop = t.scrollHeight;
                }
            }
        }
    },
    
    send: function()
    {
        var url = "/Management?send=" + escape(document.getElementById("message").value);
        var t = document.getElementById("text");
        var m = document.getElementById("message").value;
        if (m != "") {
            t.value += "\r\n" + m;
            t.scrollTop = t.scrollHeight;
        }
        document.getElementById("message").value = "";
        var req = new this.interaction(url, this.sendProcess);
        req.doGet();
        document.getElementById("send").disabled = true;
    },
    
    sendProcess: function(xml)
    {
        if (xml == null) {
            alert("Ошибка! sendProcess - response no xml");
            ining.gps.clear();
            return;
        } else {
            var c = xml.documentElement;
            var err = c.getAttribute("error");
            if (err != null) {
                alert(err);
                ining.gps.clear();
            }
            document.getElementById("send").disabled = false;
        }
    },
    
    disconnect: function()
    {
        var url = "/Management?disconnect";
        var req = new this.interaction(url, this.disconnectProcess);
        req.doGet();
    },
    
    disconnectProcess: function(xml)
    {
        if (xml != null)
        {
            var c = xml.documentElement;
            var err = c.getAttribute("error");
            if (err != null) alert(err);
        }
        ining.gps.clear();
    },
    
    interaction: function(url, callback)
    {
        var req = init();
        req.onreadystatechange = processRequest;

        function init() {
            if (window.XMLHttpRequest) {
                try {
                    return new XMLHttpRequest();
                } catch (e) {
                    return null;
                }
            } else if (window.ActiveXObject) {
                try
                {
                    return new new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try
                    {
                        return new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    catch (e) {
                        return null;
                    }
                }
            }
            return null;
        }

        function processRequest() {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    if (callback) {
                        callback(req.responseXML);
                    }
                }
            }
        }

        this.doGet = function() {
            req.open("GET", url, true);
            req.send(null);
        }

    }
    
}