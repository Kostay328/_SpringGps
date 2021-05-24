window.ining = window.ining || {};
ining.gps = ining.gps || {
    
    statusFunc: null,
    backupHref: null,
    
    createBackup: function()
    {
        var unit = document.getElementById("unit").value;
        var startts = document.getElementById("startts").value;
        var endts = document.getElementById("endts").value;
        var del = document.getElementById("del").value;
        var url = "/BackupAndRestore?action=backup&unit=" + unit + "&startts=" + startts + "&endts=" + endts;
        if (del) url += "&del";
        var req = new this.interaction(url, this.backupStatus);
        req.doGet();
    },
    
    loadBackupStatus: function()
    {
        var url = "/BackupAndRestore?action=getBackupStatus";
        var req = new this.interaction(url, this.backupStatus);
        req.doGet();
    },
    
    backupStatus: function(xml) 
    {
        if (xml == null) return;
        if (ining.gps.statusFunc == null) ining.gps.statusFunc = setInterval("ining.gps.loadBackupStatus()", 1000);
        var c = xml.documentElement;
        var msg = c.getAttribute("msg");
        var err = c.getAttribute("err");
        var done = c.getAttribute("done");
        if (err != null) {
            if (ining.gps.statusFunc != null) {
                clearInterval(ining.gps.statusFunc);
                ining.gps.statusFunc = null;
            }
            document.getElementById("btn").value = "Создать резервную копию";
            document.getElementById("msg").value = "Ошибка!";
            alert(err);
        }
        else if (done != null) {
            if (ining.gps.statusFunc != null) {
                clearInterval(ining.gps.statusFunc);
                ining.gps.statusFunc = null;
            }
            document.getElementById("btn").value = "Скачать файл с резервной копией " + done;
            document.getElementById("btn").onclick = function() { 
                document.getElementById("btn").value = "Создать резервную копию";
                document.getElementById("btn").onclick = ining.gps.createBackup;
                document.getElementById("msg").value = "";
                window.location="/BackupAndRestore?action=getFile";
            }
            document.getElementById("msg").value = "Резервное копирование завершено";
            alert("Резервное копирование завершено");
        }
        else if (msg != null) {
            document.getElementById("msg").value = msg;
        }
        else {
            if (ining.gps.statusFunc != null) {
                clearInterval(ining.gps.statusFunc);
                ining.gps.statusFunc = null;
            }
        }
    },
    
    restore: function()
    {
        if (this.statusFunc == null) this.statusFunc = setInterval("ining.gps.restoreStatus()", 1000);
    },
    
    restoreStatus: function()
    {
        var url = "/BackupAndRestore?action=restoreStatus";
        var req = new this.interaction(url, ining.gps.restoreStatusOnLoad);
        req.doGet();
    },
    
    confirmRestore: function(xml)
    {
        if (xml == null) return;
        var c = xml.documentElement;
        if (c.getAttribute("confirmed") != null) ining.gps.statusFunc = setInterval("ining.gps.restoreStatus()", 1000);
    },
    
    restoreStatusOnLoad: function(xml)
    {
        if (xml == null) return;
        var c = xml.documentElement;
        var msg = c.getAttribute("msg");
        var err = c.getAttribute("err");
        var done = c.getAttribute("done");
        var cnfrm = c.getAttribute("confirm");
        if (err != null) {
            if (ining.gps.statusFunc != null) {
                clearInterval(ining.gps.statusFunc);
                ining.gps.statusFunc = null;
            }
            document.getElementById("msgr").value = "Ошибка!";
            alert(err);
        }
        else if (cnfrm != null) {
            if (confirm(cnfrm)) {
                if (ining.gps.statusFunc != null) {
                    clearInterval(ining.gps.statusFunc);
                    ining.gps.statusFunc = null;
                }
                var url = "/BackupAndRestore?action=restoreConfirm";
                var req = new ining.gps.interaction(url, ining.gps.confirmRestore);
                req.doGet();
            } else {
                if (ining.gps.statusFunc != null) {
                    clearInterval(ining.gps.statusFunc);
                    ining.gps.statusFunc = null;
                }
                url = "/BackupAndRestore?action=restoreNotConfirm";
                req = new ining.gps.interaction(url, ining.gps.confirmRestore);
                req.doGet();
            }
        }
        else if (done != null) {
            if (ining.gps.statusFunc != null) {
                clearInterval(ining.gps.statusFunc);
                ining.gps.statusFunc = null;
            }
            document.getElementById("msgr").value = "";
            alert("Восстановление завершено");
        }
        else if (msg != null) {
            document.getElementById("msgr").value = msg;
        }
        else {
            if (ining.gps.statusFunc != null) {
                clearInterval(ining.gps.statusFunc);
                ining.gps.statusFunc = null;
            }
        }
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