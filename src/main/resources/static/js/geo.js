window.ining = window.ining || {};
ining.geo = ining.geo || {

    geoGroups: new Array(),
    geoZones: new Array(),
    currentZone: null,
    gmarkers: new Array(),
    changed: false,

    initialize : function() {
        GEvent.addListener(ining.gps.map, "click", this.leftClick);
        var req = new ining.gps.interaction("/GeoZones?action=init", this.loadGeoGroups);
        req.doGet();
    },

    leftClick: function(overlay, latlng)
    {
        var zone = ining.geo.getGeoZoneByOverlay(overlay);
        if (zone == null) {
            if (ining.geo.currentZone != null) {
                ining.gps.map.removeOverlay(ining.geo.currentZone.overlay);
                ining.geo.currentZone.latlngs.push(latlng);
                ining.geo.createGeoZonePolygon(ining.geo.currentZone);
                var marker = ining.geo.createMarker(latlng);
                ining.geo.gmarkers.push(marker);
                ining.gps.map.addOverlay(marker);
                ining.gps.map.addOverlay(ining.geo.currentZone.overlay);
                ining.geo.changed = true;
            }
        } else if (zone != ining.geo.currentZone) {
            ining.geo.selectGeoZone(zone);
        }
    },

    showCarTrack: function()
    {
        ining.gps.disableShowCars();

        var _intervals_time = 30;
        var _speed_intervals = true;
        var _car = document.getElementById("car").value;
        var _date = document.getElementById("date").value;

        var url = "/GpsData?car=" + _car + "&date=" + _date;
        if (_intervals_time != 0) url += "&intervals=" + _intervals_time;
        if (_speed_intervals) url += "&speed_intervals=on";
        url += "&rnd=" + Math.random() * 65535;

        var req = new ining.gps.interaction(url, ining.gps.showTrack);
        req.doGet();
    },

    createMarker: function(latlng)
    {
        var marker = new GMarker(latlng, {icon:G_DEFAULT_ICON, draggable:true, bouncy:false, dragCrossMove:true});

        GEvent.addListener(marker, "drag", function() {
            ining.gps.map.removeOverlay(ining.geo.currentZone.overlay);
            ining.geo.currentZone.latlngs = new Array();
            for (i = 0; i < ining.geo.gmarkers.length; i++)
                ining.geo.currentZone.latlngs.push(ining.geo.gmarkers[i].getLatLng());
            ining.geo.createGeoZonePolygon(ining.geo.currentZone);
            ining.gps.map.addOverlay(ining.geo.currentZone.overlay);
            ining.gps.geo.changed = true;
        });

        GEvent.addListener(marker, "click", function() {
            if (confirm("Удалить точку?")) {
                ining.gps.map.removeOverlay(ining.geo.currentZone.overlay);
                ining.geo.currentZone.latlngs = new Array();
                for (i = 0; i < ining.geo.gmarkers.length; i++)
                    if (ining.geo.gmarkers[i] == marker) {
                        ining.gps.map.removeOverlay(ining.geo.gmarkers[i]);
                        ining.geo.gmarkers.splice(i, 1);
                        break;
                    }
                for (i = 0; i < ining.geo.gmarkers.length; i++)
                    ining.geo.currentZone.latlngs.push(ining.geo.gmarkers[i].getLatLng());
                ining.geo.createGeoZonePolygon(ining.geo.currentZone);
                ining.gps.map.addOverlay(ining.geo.currentZone.overlay);
                ining.gps.geo.changed = true;
            }
        });

        return marker;
    },

    createGeoZonePolygon: function(zone)
    {
        if (zone.latlngs.length > 2) {
            zone.latlngs.push(zone.latlngs[0]);
            zone.overlay = new GPolygon(zone.latlngs, "#0000af", 3, .8, "#335599", .2);
            zone.latlngs.pop();
        } else zone.overlay = new GPolygon(zone.latlngs, "#0000af", 3, .8, "#335599", .2);
    },

    updateGeoGroupsList: function()
    {
        var url = "/GeoZones?action=get-geo-groups";
        showRemoved = document.getElementById("show-removed").checked;
        if (showRemoved) url += "&show-removed=true";
        var req = new ining.gep.interaction(url, ining.geo.loadGeoGroups);
        req.doGet();
    },

    setGeoGroupsButtonsStatus: function(value)
    {
        document.getElementById("group-status").disabled = value;
        document.getElementById("group-rename").disabled = value;
        document.getElementById("zone-add").disabled = value;
    },

    disableGeoGroupsButtons: function()
    {
        document.getElementById("selected-group-name").value = "";
        document.getElementById("group-create-ts").innerHTML = "";
        document.getElementById("group-remove-ts").innerHTML = "";
        this.setGeoGroupsButtonsStatus(true);
    },

    enableGeoGroupsButtons: function()
    {
        this.setGeoGroupsButtonsStatus(false);
    },

    clearGeoGroups: function()
    {
        while (ining.geo.geoGroups.length > 0) ining.geo.geoGroups.pop();
        var e = document.getElementById("groups");
        while (e.hasChildNodes()) e.removeChild(e.lastChild);
    },

    loadGeoGroups: function(xml)
    {
        ining.geo.disableGeoGroupsButtons();
        ining.geo.clearGeoGroups();
        var groups = xml.documentElement.getElementsByTagName("group");
        for (var i = 0; i < groups.length; i++) {
            group = new Array(4);
            group[0] = parseInt(groups[i].getAttribute("id"));
            group[1] = groups[i].getAttribute("name");
            group[2] = groups[i].getAttribute("crtts");
            group[3] = groups[i].getAttribute("rmts");
            if (group[3] == "") group[3] = "---";
            ining.geo.geoGroups.push(group);
        }
        eGroups = document.getElementById("groups");
        for (i = 0; i < ining.geo.geoGroups.length; i++) {
            eOption = document.createElement("option");
            eOption.value = ining.geo.geoGroups[i][0];
            eOption.innerHTML = ining.geo.geoGroups[i][1];
            eGroups.appendChild(eOption);
        }
        ining.geo.clearGeoZones();
        ining.geo.setGeoZonesButtonsStatus(true);
        document.getElementById("groups").disabled = ining.geo.geoGroups.length == 0;
    },

    onChangeGeoGroup: function()
    {
        this.clearGeoZones();
        var eGroups = document.getElementById("groups");
        var index = eGroups.selectedIndex;
        document.getElementById("selected-group-name").value = this.geoGroups[index][1];
        document.getElementById("group-create-ts").innerHTML = this.geoGroups[index][2];
        var rmts = this.geoGroups[index][3];
        document.getElementById("group-remove-ts").innerHTML = rmts;
        document.getElementById("group-status").value = rmts == "---" ? "Деактивировать" : "Активировать";
        this.enableGeoGroupsButtons();
        this.loadGeoZones();
    },

    createGeoGroup: function()
    {
        this.disableGeoGroupsButtons();
        var eName = document.getElementById("group-name");
        var name = eName.value;
        eName.value = "";
        if (name == "") alert("Название группы не введено!");
        else {
            var url = "/GeoZones?action=create-group&name=" + name;
            var showRemoved = document.getElementById("show-removed").checked;
            if (showRemoved) url += "&show-removed=true";
            var req = new ining.gps.interaction(url, ining.geo.loadGeoGroups);
            req.doGet();
        }
    },

    renameGeoGroup: function()
    {
        var eName = document.getElementById("selected-group-name");
        name = eName.value;
        var eGroups = document.getElementById("groups");
        var index = eGroups.selectedIndex;
        this.disableGeoGroupsButtons();
        if (name == "") alert("Название группы не введено! ");
        else {
            var url = "/GeoZones?action=rename-group&name=" + name;
            url += "&id=" + this.geoGroups[index][0];
            var showRemoved = document.getElementById("show-removed").checked;
            if (showRemoved) url += "&show-removed=true";
            var req = new ining.gps.interaction(url, ining.geo.loadGeoGroups);
            req.doGet();
        }
    },

    changeGeoGroupStatus: function()
    {
        var eGroups = document.getElementById("groups");
        var index = eGroups.selectedIndex;
        var req;
        if (this.geoGroups[index][3] == "---") {
            if (confirm("Деактивировать группу?")) {
                url = "/GeoZones?action=change-group-status&status=disable";
                url += "&id=" + this.geoGroups[index][0];
                showRemoved = document.getElementById("show-removed").checked;
                if (showRemoved) url += "&show-removed=true";
                req = new ining.gps.interaction(url, ining.geo.loadGeoGroups);
                req.doGet();
            }
        } else {
            if (confirm("Активировать группу?")) {
                url = "/GeoZones?action=change-group-status&status=enable";
                url += "&id=" + this.geoGroups[index][0];
                showRemoved = document.getElementById("show-removed").checked;
                if (showRemoved) url += "&show-removed=true";
                req = new ining.gps.interaction(url, ining.geo.loadGeoGroups);
                req.doGet();
            }
        }
    },

    // = GeoZones ==============================================================

    setGeoZonesButtonsStatus: function(value)
    {
        if (value) document.getElementById("zone-name").value = "";
        document.getElementById("zones").disabled = value;
        document.getElementById("zone-name").innerHTML = "";
        document.getElementById("zone-name").disabled = value;
        document.getElementById("zone-rename").disabled = value;
        document.getElementById("save-zones").disabled = !this.changed;
        document.getElementById("zone-remove").disabled = value;
    },

    removeZone: function()
    {
        if (this.currentZone != null) {
            if (confirm("Удалить гео-зону?")) {
                this.changed = true;
                this.clearMarkers();
                this.removeGeoZone(ining.geo.currentZone);
                this.setGeoZonesButtonsStatus(false);
                this.updateGeoZoneList();
                this.currentZone = null;
            }
        }
    },

    addGeoZone: function()
    {
        this.changed = true;
        var zone = new Object();
        zone.latlngs = new Array();
        this.createGeoZonePolygon(zone);
        zone.name = "Без названия";
        zone.id = 0;
        this.geoZones.push(zone);
        ining.gps.map.addOverlay(zone.overlay);
        this.setGeoZonesButtonsStatus(false);
        this.updateGeoZoneList();
        this.selectGeoZone(zone);
    },

    clearMarkers: function()
    {
        while (this.gmarkers.length > 0) {
            marker = this.gmarkers.pop();
            ining.gps.map.removeOverlay(marker);
        }
    },

    createMarkers: function(zone)
    {
        for (i = 0; i < zone.latlngs.length; i++) {
            point = zone.latlngs[i];
            var marker = this.createMarker(point);
            this.gmarkers.push(marker);
            ining.gps.map.addOverlay(marker);
        }
    },

    selectGeoZone: function(zone)
    {
        var index = this.getGeoZoneIndex(zone);
        document.getElementById("zones").selectedIndex = index;
        document.getElementById("zone-name").value = zone.name;
        if (zone.latlngs.length != 0) ining.gps.map.panTo(zone.latlngs[0]);
        this.currentZone = zone;
        this.clearMarkers();
        this.createMarkers(this.currentZone);
    },

    removeGeoZone: function(zoneForRemove)
    {
        for (var i = 0; i < this.geoZones.length; i++) {
            var zone = this.geoZones[i];
            if (zone == zoneForRemove) {
                ining.gps.map.removeOverlay(zone.overlay);
                this.geoZones.splice(i, 1);
                this.changed = true;
                return;
            }
        }
    },

    removeGeoZoneByOverlay: function(overlay)
    {
        var zone = this.getGeoZoneByOverlay(overlay);
        this.removeGeoZone(zone);
    },

    getGeoZoneByOverlay: function(overlay)
    {
        for (var i = 0; i < this.geoZones.length; i++) {
            var zone = this.geoZones[i];
            if (zone.overlay == overlay) return zone;
        }
        return null;
    },

    getGeoZoneIndex: function(zone)
    {
        for (var i = 0; i < this.geoZones.length; i++)
            if (zone == this.geoZones[i]) return i;
        return 0;
    },

    clearGeoZones: function()
    {
        this.setGeoZonesButtonsStatus(true);
        this.clearMarkers();
        while (this.geoZones.length > 0) {
            zone = this.geoZones.pop();
            ining.gps.map.removeOverlay(zone.overlay);
        }
        var eZones = document.getElementById("zones");
        while(eZones.hasChildNodes()) eZones.removeChild(eZones.lastChild);
        this.currentZone = null;
    },

    updateGeoZoneList: function()
    {
        var eZones = document.getElementById("zones");
        while (eZones.hasChildNodes()) eZones.removeChild(eZones.lastChild);
        for (i = 0; i < this.geoZones.length; i++) {
            var zone = this.geoZones[i];
            eOption = document.createElement("option");
            eOption.value = zone.id;
            eOption.innerHTML = zone.name;
            eZones.appendChild(eOption);
        }
        if (this.geoZones.length == 0) this.setGeoZonesButtonsStatus(true);
    },

    onChangeGeoZone: function()
    {
        var index = document.getElementById("zones").selectedIndex;
        var zone = this.geoZones[index];
        this.selectGeoZone(zone);
    },

    renameGeoZone: function()
    {
        this.changed = true;
        var eZones = document.getElementById("zones");
        var index = eZones.selectedIndex;
        var zone = this.geoZones[index];
        var name = document.getElementById("zone-name").value;
        zone.name = name;
        eZones.childNodes[index].innerHTML = name;
        this.setGeoZonesButtonsStatus(false);
    },

    saveZones: function()
    {
        var eGroups = document.getElementById("groups");
        var index = eGroups.selectedIndex;
        var req;

        var str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        str += "<group action=\"save\" id=\"" + eGroups.childNodes[index].value + "\" name=\"" + eGroups.childNodes[index].innerHTML + "\">";
        for (var i = 0; i < this.geoZones.length; i++) {
            var zone = this.geoZones[i];
            str += "<zone id=\"" + zone.id + "\" name=\"" + zone.name + "\">";
            for (j = 0; j < zone.latlngs.length; j++) {
                str += "<point lat=\"" + zone.latlngs[j].lat() + "\" lng=\"" + zone.latlngs[j].lng() + "\"/>";
            }
            str += "</zone>";
        }
        str += "</group>";
        //alert(str);
        req = new ining.gps.interaction("/GeoZones", ining.geo.saveGeoZonesFunc);
        req.doPostXML(str);
    },

    saveGeoZonesFunc: function(xml)
    {
        var content = xml.documentElement;
        if (content.getAttribute("result") == "ok") {
            ining.geo.changed = false;
            alert("Гео-зоны сохранены.");
        }
        else alert("Ошибка при сохранении гео-зон: " + content.getAttribute("message"));
    },

    loadGeoZones: function()
    {
        var eGroups = document.getElementById("groups");
        var index = eGroups.selectedIndex;
        var str = "/GeoZones?action=get-geo-zones";
        str += "&group=" + this.geoGroups[index][0];
        var req = new ining.gps.interaction(str, ining.geo.loadGeoZonesFunc);
        req.doGet();
    },

    loadGeoZonesFunc: function(xml)
    {
        ining.geo.changed = false;
        ining.geo.clearGeoZones();
        var zones = xml.documentElement.getElementsByTagName("zone");
        for (var i = 0; i < zones.length; i++) {
            var zone = new Object();
            zone.id = parseInt(zones[i].getAttribute("id"));
            zone.name = zones[i].getAttribute("name")
            zone.latlngs = new Array();
            var points = zones[i].getElementsByTagName("point");
            for (var j = 0; j < points.length; j++) {
                zone.latlngs.push(new GLatLng(
                    parseFloat(points[j].getAttribute("lat")),
                    parseFloat(points[j].getAttribute("lng"))
                ))
            }
            ining.geo.geoZones.push(zone);
            ining.geo.createGeoZonePolygon(zone);
            ining.gps.map.addOverlay(zone.overlay);
        }
        ining.geo.setGeoZonesButtonsStatus(false);
        ining.geo.updateGeoZoneList();
    }

}