window.ining = window.ining || {};
ining.gps = ining.gps || {

    enableDisableCoordinate: function(id)
    {
        var url = "/GpsData?edid=" + id;
        var req = new this.interaction(url, null);
        req.doGet();
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
                        document.body.style.cursor = 'default';
                        cursorSatatus = 'default';
                    }
                } else window.location.replace("/");
            }
        }

        this.doGet = function() {
            req.open("GET", url, true);
            req.send(null);
        }

        this.doPost = function(body) {
            req.open("POST", url, true);
            req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            req.send(body);
        }

        this.doPostXML = function(body) {
            req.open("POST", url, true);
            req.setRequestHeader("Content-Type", "application/xml");
            req.setRequestHeader("Content-Length", ining.gps.getStringSize(body));
            req.send(body);
        }
    }

}