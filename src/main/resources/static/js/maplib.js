/// !!!Nik
function change_tz(str) {
  st= str[0] + str[1];
  tom = '';
  if (st == '00') {
    st = '23';
    tom = ' предыдущего дня';
  } else {
    hr = parseInt(st) - 1;
    st = hr.toString();
    while (st.length < 2) {
      st = '0' + st;
    }
  }
  str = st + str.substr(2) + tom;
  return str;
}
// !!!Nik

window.ining = window.ining || {};
ining.gps = ining.gps || {
    map: null,
    polylines: new Array(),
    markers: new Array(),
    zpl: new Array(),

    mi: new Array(16),
    pi: null,
    pn: null,
    pf: null,

    fullScreen: false,

    showCarFunc: null,

    init: function() {
        var emap = document.getElementById("map");
        if (emap == null) return;

        this.resize();

        //!!!3
        this.map = new google.maps.Map(
            emap, {
                center: new google.maps.LatLng(ining.defLat, ining.defLng),
                zoom: ining.defZoom,
                overviewMapControl: true,
                overviewMapControlOptions: {opened : true},
                mapTypeId: google.maps.MapTypeId.ROADMAP
            }
        ); 
    
        /*!!!3
        this.map.addControl(new GLargeMapControl());
        this.map.addControl(new GMapTypeControl());
        this.map.addControl(new GOverviewMapControl());
        this.map.setCenter(new google.maps.LatLng(ining.defLat, ining.defLng), ining.defZoom);
        this.map.addControl(new ining.gps.FullScreenControl());
        */

        for (i = 0; i < 16; i++) {
            //!!!3
            //this.mi[i] = new GIcon(G_DEFAULT_ICON);
            //this.mi[i].image = "/Resource?id=images/marker" + i + ".png";
            this.mi[i] = "/Resource?id=images/marker" + i + ".png";
        }

        //!!!3
        //this.pi = new GIcon(G_DEFAULT_ICON);
        //this.pi.image = "/Resource?id=images/parking.png";
        this.pi = "/Resource?id=images/parking.png";
        
        //!!!3
        //this.pn = new GIcon(G_DEFAULT_ICON);
        //this.pn.image = "/Resource?id=images/pon.png";
        this.pn = "/Resource?id=images/pon.png";
        
        //!!!3
        //this.pf = new GIcon(G_DEFAULT_ICON);
        //this.pf.image = "/Resource?id=images/poff.png";
        this.pf = "/Resource?id=images/poff.png";

        if (ining.loadCar != 0) this.loadCarTrack(ining.loadCar, ining.loadDate, ining.loadFrom, ining.loadTo, ining.loadZones);
    },

    done: function() {
        //!!!3 не используется
        //GUnload();
    },

    resize: function() {
        var emap = document.getElementById("map");
        if (emap == null) return;

        if (this.fullScreen) {
            emap.style.width = "100%";
            emap.style.height = "100%";
            return;
        }

        var height = null;
        //var width = null;
        if(typeof(window.innerWidth) == 'number') {
            height = window.innerHeight;
            //width = window.innerWidth;
        } else if(document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
            height = document.documentElement.clientHeight;
            //width = document.documentElement.clientWidth;
        } else if(document.body && (document.body.clientWidth || document.body.clientHeight)) {
            height = document.body.clientHeight;
            //width = document.body.clientWidth;
        }

        if (height == null || height < 768) height = 768;
        emap.style.height = height - 114 + "px";
        //emap.style.width = width - 287 + "px";
        //alert(emap.style.width + "x" + emap.style.height);
    },

    toCenter: function() {
        //!!!3
        //this.map.setCenter(new google.maps.LatLng(ining.defLat, ining.defLng), ining.defZoom);
        this.map.setCenter(new google.maps.LatLng(ining.defLat, ining.defLng));
    },

    // =========================================================================

    clearMap: function() {
        //!!!3
        // while (this.polylines.length > 0) this.map.removeOverlay(this.polylines.pop());
        // while (this.markers.length > 0) this.map.removeOverlay(this.markers.pop());
        // while (this.zpl.length > 0) this.map.removeOverlay(this.zpl.pop());
        while (this.polylines.length > 0) {
            this.polylines.pop().setMap(null);
        }
        while (this.markers.length > 0) {
            this.markers.pop().setMap(null);
        }
        while (this.zpl.length > 0) {
            this.zpl.pop().setMap(null);
        }
    },

    getSpeedColor: function(value, sc) {
        if (sc == 1) {
            if (value == 1) return "#8080FF";
            if (value == 2) return "#008000";
            if (value == 3) return "#d0d000";
            if (value == 4) return "#ff8000";
            if (value == 5) return "#ff0000";
            if (value == 6) return "#ff00ff";
        } else {
            if (value == 1) return "#01dbff";
            if (value == 2) return "#29b9ff";
            if (value == 3) return "#5692ff";
            if (value == 4) return "#836bff";
            if (value == 5) return "#ae45ff";
            if (value == 6) return "#fe01ff";
        }
        return "#000000";
    },
    
    getSpeedColor2: function(value) {
        if (value >= 0 && value < 10) return "#8080FF";
        if (value >= 10 && value < 40) return "#008000";
        if (value >= 40 && value < 60) return "#d0d000";
        if (value >= 60 && value < 90) return "#ff8000";
        if (value >= 90 && value < 110) return "#ff0000";
        return "#ff00ff";
    },

    getIconIndex: function(angle) {
        // !!!3 предположительно была ошибка (mi[0] вместо 0)
        if (angle == null) return 0;
        angle += 90;
        if (angle > 360) angle -= 360;
        angle = Math.round(angle / 22,5);
        if (angle > 15) angle = 15;
        return angle;
    },

    setHTML: function(id, value) {
        var e = document.getElementById(id);
        if (e != null) e.innerHTML = value;
    },

    getStringSize: function(str) {
        size = 0;
        for (var i = 0; i < str.length; i++ ) {
            ch = str.charCodeAt(i);
            do {
                size++
                ch = ch >> 8;
            }
            while ( ch );
        }
        return size;
    },
    
    // = Enable/Disable coordinate =============================================
    
    enableDisableCoordinate: function(id) {
        var url = "/GpsData?edid=" + id;
        var req = new this.interaction(url, null);
        req.doGet();
    },
    
    // = Track =================================================================

    loadTrack: function() {
        this.disableShowCars();

        this.setHTML("track-length", "0");
        this.setHTML("max-speed", "0");
        this.setHTML("start-time", "...");
        this.setHTML("end-time", "...");
        this.setHTML("point-count", "0");

        var _car = document.getElementById("car").value;
        var _date = document.getElementById("date").value;
        var _from = document.getElementById("from").value;
        var _to = document.getElementById("to").value;
        var _intervals_time = document.getElementById("intervals-time").value;
        var _speed_intervals = document.getElementById("speed-intervals").checked;
        var _power = document.getElementById("power").checked;

        var url = "/GpsData?car=" + _car + "&date=" + _date;
        if (_from != '') url += "&from=" + _from;
        if (_to != '') url += "&to=" + _to;
        if (_intervals_time != 0) url += "&intervals=" + _intervals_time;
        if (_speed_intervals) url += "&speed_intervals=on";
        if (_power) url += "&power=on";
        url += "&rnd=" + Math.random() * 65535;

        var req = new this.interaction(url, this.showTrack);
        req.doGet();
    },

    removeDoubledCoordsInTrack: function() {
        var _car = document.getElementById("car").value;
        var _date = document.getElementById("date").value;
        var url = "/GpsData?action=removeDoubles&car=" + _car + "&date=" + _date;
        var req = new this.interaction(url, this.updateTrack);
        req.doGet();
    },

    updateTrack: function(xml) {
        ining.gps.loadTrack();
    },

    loadCarTrack: function(id, date, from, to, zones) {
        this.disableShowCars();

        var list = document.getElementById("car");
        for (i = 0; i < list.length; i++)
            list.options[i].selected = list.options[i].value == id;

        this.setHTML("track-length", "0");
        this.setHTML("max-speed", "0");
        this.setHTML("start-time", "...");
        this.setHTML("end-time", "...");
        this.setHTML("point-count", "0");

        var _intervals_time = document.getElementById("intervals-time").value;
        var _speed_intervals = document.getElementById("speed-intervals").checked;
        var _car = id;
        var _date = date;
        var _from = from;
        var _to = to;

        var url = "/GpsData?car=" + _car + "&date=" + _date;
        if (_from != null) url += "&from=" + _from;
        if (_to != null) url += "&to=" + _to;
        if (zones != null) url += "&zones=" + zones;
        if (_intervals_time != 0) url += "&intervals=" + _intervals_time;
        if (_speed_intervals) url += "&speed_intervals=on";
        url += "&rnd=" + Math.random() * 65535;

        var req = new this.interaction(url, this.showTrack);
        req.doGet();
    },

    showTrack: function(xml) {
        ining.gps.disableShowCars();
        ining.gps.clearMap();

        var c = xml.documentElement;
        var msg = c.getAttribute("error");
        if (msg != null) {
            alert(msg);
            return;
        }

        var p = null;
        var cp = null;
        var pl = c.getElementsByTagName("p");
        var zl = c.getElementsByTagName("z");
        var lat = 0;
        var lng = 0;

        if (zl != null && zl.length > 0) {
            var f = null;
            for (var q = 0; q < zl.length; q++) {
                var z = zl[q];
                var cl = z.getElementsByTagName("c");
                var pc = new Array();
                for (var w = 0; w < cl.length; w++) {
                    var g = new google.maps.LatLng(parseFloat(cl[w].getAttribute("x")), parseFloat(cl[w].getAttribute("y")));
                    if (w == 0) f = g;
                    pc.push(g);
                }
                pc.push(f);
                //!!!3
                //ining.gps.zpl.push(new GPolygon(pc, "#0000af", 3, .8, "#335599", .2));
                ining.gps.zpl.push(new GPolygon(pc, "#0000af", 3, .8, "#335599", .2));
                var bermudaTriangle = new google.maps.Polygon({
                    paths: triangleCoords,
                    strokeColor: '#FF0000',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#FF0000',
                    fillOpacity: 0.35
                });
                bermudaTriangle.setMap(map);
            }
        }
        
        if (pl != null && pl.length > 0) {
            ining.gps.setHTML("point-count", c.getAttribute("count"));
            ining.gps.setHTML("track-length", c.getAttribute("length"));
            ining.gps.setHTML("start-time", c.getAttribute("start-time"));
            ining.gps.setHTML("end-time", c.getAttribute("end-time"));
            ining.gps.setHTML("max-speed", c.getAttribute("max-speed"));

            lat = parseFloat(pl[0].getAttribute("x"));
            lng = parseFloat(pl[0].getAttribute("y"));
            p = new google.maps.LatLng(lat, lng);
            ining.gps.map.panTo(p);

            /*!!!3
            var mo = {
                title: c.getAttribute("name") +
                    ", Время: " + c.getAttribute("end-time") +
                    ", Скорость: " + pl[0].getAttribute("sp") + " км/ч",
                icon: ining.gps.mi[ining.gps.getIconIndex(parseInt(pl[0].getAttribute("c")))]
            };*/

            //!!!3
            //ining.gps.markers.push(new GMarker(p, mo));
            ining.gps.markers.push(new google.maps.Marker({
                position: p,
                map: ining.gps.map,
                title: c.getAttribute("name") +
                    ", Время: " + c.getAttribute("end-time") +
                    ", Скорость: " + pl[0].getAttribute("sp") + " км/ч",
                icon: ining.gps.mi[ining.gps.getIconIndex(parseInt(pl[0].getAttribute("c")))]
            }));

            var pws = pl[0] == 0;
            var sc = 1;
            e = document.getElementById("sc2");
            if (e && e.checked) sc = 2;
            e = document.getElementById("speed-intervals");
            var si = e ? e.checked : true;
            var speed = 0;
            var index = 0;
            if (si) speed = parseInt(pl[0].getAttribute("s"));
            //!!!3
            // if (pws) cp = new GPolyline([], "#000000", 7, 0.8, {clickable:false, geodesic:false});
            // else cp = new GPolyline([], si ? ining.gps.getSpeedColor(speed, sc) : "#ff0000", 7, 0.8, {clickable:false, geodesic:false});
            if (pws) {
                cp = new google.maps.Polyline({
                    strokeColor: '#000000',
                    strokeOpacity: 0.8,
                    strokeWeight: 7,
                    clickable:false,
                    geodesic: false
                });
            } else {
                cp = new google.maps.Polyline({
                    strokeColor: si ? ining.gps.getSpeedColor(speed, sc) : "#ff0000",
                    strokeOpacity: 0.8,
                    strokeWeight: 7,
                    clickable:false,
                    geodesic: false
                });
            }

            //!!!3
            // ining.gps.map.addOverlay(cp);
            cp.setMap(ining.gps.map);

            ining.gps.polylines.push(cp);

            for (var i = 0; i < pl.length; i++) {
                
                lat = parseFloat(pl[i].getAttribute("x"));
                lng = parseFloat(pl[i].getAttribute("y"));
                p = new google.maps.LatLng(lat, lng);
                //!!!3
                //cp.insertVertex(index++, p);
                index++;
                cp.getPath().push(p);
                
                if (pl[i].getAttribute("pn") != null) {
                    //!!!3
                    // var mw = {
                    //     title: pl[i].getAttribute("pt"),
                    //     icon: ining.gps.pf
                    // };
                    //ining.gps.markers.push(new GMarker(p, mw));
                    ining.gps.markers.push(new google.maps.Marker({
                        position: p,
                        map: ining.gps.map,
                        title: pl[i].getAttribute("pt"),
                        icon: ining.gps.pf
                    }));
                    pws = false;
                    index = 51;
                }
                
                if (pl[i].getAttribute("pf") != null) {
                    //!!!3
                    // var mf = {
                    //     title: pl[i].getAttribute("pt"),
                    //     icon: ining.gps.pn
                    // };
                    // ining.gps.markers.push(new GMarker(p, mf));
                    ining.gps.markers.push(new google.maps.Marker({
                        position: p,
                        map: ining.gps.map,
                        title: pl[i].getAttribute("pt"),
                        icon: ining.gps.pn
                    }));
                    pws = true;
                    index = 51;
                }
                
                if (si && i > 0) {
                    s = pl[i].getAttribute("s");
                    if (s != null && s != speed) {
                        speed = parseInt(s);
                        index = 51;
                    }
                }
                
                if (index > 50) {
                    index = 0;
                    //!!!3
                    //if (pws) cp = new GPolyline([], "#000000", 7, 0.8, {clickable:false, geodesic:false});
                    //else cp = new GPolyline([], si ? ining.gps.getSpeedColor(speed, sc) : "#ff0000", 7, 0.8, {clickable:false, geodesic:false});
                    if (pws) {
                        cp = new google.maps.Polyline({
                            strokeColor: '#000000',
                            strokeOpacity: 0.8,
                            strokeWeight: 7,
                            clickable:false,
                            geodesic: false
                        });
                    } else {
                        cp = new google.maps.Polyline({
                            strokeColor: si ? ining.gps.getSpeedColor(speed, sc) : "#ff0000",
                            strokeOpacity: 0.8,
                            strokeWeight: 7,
                            clickable:false,
                            geodesic: false
                        });
                    }

                    //!!!3
                    // ining.gps.map.addOverlay(cp);
                    cp.setMap(ining.gps.map);

                    ining.gps.polylines.push(cp);
                    //!!!3
                    //cp.insertVertex(index++, p);
                    cp.getPath().push(p);
                }
                
                pa = pl[i].getAttribute("p");
                if (pa != null) {
                    // !!!Nik
                    pa = pa.substr(0, 2) + change_tz(pa.substr(2, 8)) + pa.substr(10);
                    // !!!

                    //!!!3
                    // mo = {
                    //     title: pa,
                    //     icon: ining.gps.pi
                    // };
                    // ining.gps.markers.push(new GMarker(p, mo));
                    ining.gps.markers.push(new google.maps.Marker({
                        position: p,
                        map: ining.gps.map,
                        title: pa,
                        icon: ining.gps.pi
                    }));

                    //!!!3Rectangle
                    var r = new ining.gps.Rectangle(ining.gps.map);
                    r.latlng_ = p;
                    r.msg_ = pa;
                    r.len_ = 130;
                    r.color = "white";
                    ining.gps.markers.push(r);
                }
            }

            //!!!3
            // for (i = ining.gps.markers.length - 1; i >= 0; i--) ining.gps.map.addOverlay(ining.gps.markers[i]);
            // for (i = ining.gps.zpl.length - 1; i >= 0; i--) ining.gps.map.addOverlay(ining.gps.zpl[i]);
            for (i = ining.gps.markers.length - 1; i >= 0; i--) ining.gps.markers[i].setMap(ining.gps.map);
            for (i = ining.gps.zpl.length - 1; i >= 0; i--) ining.gps.zpl[i].setMap(ining.gps.map);

            var length = 0;
            for (i = 0; i < ining.gps.polylines.length; i++) length += ining.gps.polylines[i].getPath().length;
        }
        else alert("Данные о маршруте отсутствуют.");
    },

    // = All cars ==================================================================

    enableShowCars: function() {
        if (this.showCarFunc == null) {
            this.showCarFunc = setInterval("ining.gps.loadCarPositions()", 5000);
            this.loadCarPositions();
        }
    },

    disableShowCars: function() {
        if (this.showCarFunc != null) {
            clearInterval(this.showCarFunc);
            this.showCarFunc = null;
        }
    },

    loadCarPositions: function() {
        ining.gps.setHTML("track-length", "0");
        ining.gps.setHTML("max-speed", "0");
        ining.gps.setHTML("start-time", "...");
        ining.gps.setHTML("end-time", "...");
        ining.gps.setHTML("point-count", "0");

        var url = "/GpsData?action=showAllCars&rnd=" + Math.random() * 65535;
        var req = new ining.gps.interaction(url, this.showCars);
        req.doGet();
    },

    showCars: function(xml) {
        ining.gps.clearMap();
        var c = xml.documentElement;
        var msg = c.getAttribute("error");
        if (msg != null) {
            alert(msg);
            return;
        }

        var pl = c.getElementsByTagName("p");
        if (pl != null && pl.length > 0) {
            for (var i = 0; i < pl.length; i++) {
                var lat = parseFloat(pl[i].getAttribute("lat"));
                var lng = parseFloat(pl[i].getAttribute("lng"));
                //!!!3
                //var p = new google.maps.LatLng(lat, lng);
                var p = new google.maps.LatLng(lat, lng);
                var t = pl[i].getAttribute("name");
                if (t == "") t = pl[i].getAttribute("no");
                var pt = t;
                t += ", " + pl[i].getAttribute("model");
                // !!!Nik
                t += ", Время: " + change_tz(pl[i].getAttribute("ts"));
                // !!!Nik
                t += " (" + pl[i].getAttribute("speed") + " км/ч)";
                t += " [" + pl[i].getAttribute("id") + "]";
                //!!!3
                // var mo = {
                //     title: t,
                //     icon: ining.gps.mi[ining.gps.getIconIndex(parseInt(pl[i].getAttribute("course")))]
                // };
                // ining.gps.markers.push(new GMarker(p, mo));
                ining.gps.markers.push(new google.maps.Marker({
                    position: p,
                    map: ining.gps.map,
                    title: t,
                    icon: ining.gps.mi[ining.gps.getIconIndex(parseInt(pl[i].getAttribute("course")))]
                }));
                //!!!3Rectangle
                var r = new ining.gps.Rectangle(ining.gps.map);
                r.latlng_ = p;
                r.msg_ = pt;
                r.len_ = 50;
                r.color = ining.gps.getSpeedColor2(parseInt(pl[i].getAttribute("speed")));
                ining.gps.markers.push(r);
            }
            //!!!3
            //for (i = ining.gps.markers.length - 1; i >= 0; i--) ining.gps.map.addOverlay(ining.gps.markers[i]);
            for (i = ining.gps.markers.length - 1; i >= 0; i--) 
                ining.gps.markers[i].setMap(ining.gps.map);
        }
        else {
            ining.gps.disableShowCars();
            alert("Нет активных автомобилей");
        }
    },

    // = XML ===================================================================

    interaction: function(url, callback) {
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

// = Fullscreen control ========================================================

//!!!3
// ining.gps.FullScreenControl = function() {}

// ining.gps.FullScreenControl.prototype = new GControl();

// ining.gps.FullScreenControl.prototype.initialize = function(map)
// {
//     var container = document.createElement("div");
//     this.setButtonStyle_(container);
//     container.innerHTML = "Развернуть";
//     GEvent.addDomListener(container, "click", function() {
//         var e = document.getElementById("map");
//         if (!ining.gps.fullScreen) {
//             e.style.position = "absolute";
//             e.style.left = 0;
//             e.style.top = 0;
//             e.style.width = "100%";
//             e.style.height = "100%";
//             container.innerHTML = "Свернуть";
//             ining.gps.fullScreen = true;
//         } else {
//             e.style.position = "relative";
//             ining.gps.fullScreen = false;
//             ining.gps.resize();
//             container.innerHTML = "Развернуть";
//         }
//     });
//     map.getContainer().appendChild(container);
//     return container;
// }

// ining.gps.FullScreenControl.prototype.getDefaultPosition = function()
// {
//     return new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(7, 30));
// }

// ining.gps.FullScreenControl.prototype.setButtonStyle_ = function(button)
// {
//     button.style.backgroundColor = "white";
//     button.style.font = "small Arial";
//     button.style.border = "1px outset black";
//     button.style.padding = "2px";
//     button.style.marginBottom = "3px";
//     button.style.textAlign = "center";
//     button.style.width = "6em";
//     button.style.cursor = "pointer";
// }

var container = document.createElement("div");
container.id = "FullScreenControl";
container.style.backgroundColor = "white";
container.style.font = "small Arial";
container.style.border = "1px outset black";
container.style.padding = "2px";
container.style.marginBottom = "3px";
container.style.textAlign = "center";
container.style.width = "6em";
container.style.cursor = "pointer";

container.innerHTML = "Развернуть";
//TODO???
//map.controls[google.maps.ControlPosition.TOP_RIGHT].push(container);

google.maps.event.addDomListener(container, "click", function() {
    var e = document.getElementById("map");
    if (!ining.gps.fullScreen) {
        e.style.position = "absolute";
        e.style.left = 0;
        e.style.top = 0;
        e.style.width = "100%";
        e.style.height = "100%";
        container.innerHTML = "Свернуть";
        ining.gps.fullScreen = true;
    } else {
        e.style.position = "relative";
        ining.gps.fullScreen = false;
        ining.gps.resize();
        container.innerHTML = "Развернуть";
    }
});


// = Rectangle =============================================================

ining.gps.Rectangle = function(map) {
    this.latlng_ = null;
    this.msg_ = null;
    this.len_ = 0;
    this.color = null;
    //!!!3
    this.map_ = map;
    this.setMap(map);
}

//!!!3
//ining.gps.Rectangle.prototype = new GOverlay();
ining.gps.Rectangle.prototype = new google.maps.OverlayView();

ining.gps.Rectangle.prototype.onAdd = function() {
    var div = document.createElement("div");
    div.style.border = "2px solid #707070";
    div.style.position = "absolute";
    //!!!3
    //this.map_ = map;
    this.div_ = div;
    //!!!3
    //map.getPane(G_MAP_MAP_PANE).appendChild(div);
    var panes = this.getPanes();
    panes.overlayLayer.appendChild(div);
}

ining.gps.Rectangle.prototype.onRemove = function() {
    //!!!3
    //this.div_.parentNode.removeChild(this.div_);
    this.div_.parentNode.removeChild(this.div_);
    this.div_ = null;
}

//!!!3
// ining.gps.Rectangle.prototype.copy = function() {
//     return new Rectangle(this.latlng_, this.msg_);
// }

//!!!3
//ining.gps.Rectangle.prototype.redraw = function(force) {
ining.gps.Rectangle.prototype.draw = function() {
    //!!!3
    //if (!force) return;
    //var c = this.map_.fromLatLngToDivPixel(this.latlng_);
    var c = this.getProjection().fromLatLngToDivPixel(this.latlng_);

    this.div_.style.font = "12px Arial,Helvetica,sans-serif";
    this.div_.style.color = "#000000";
    this.div_.style.width = this.len_ + "px";
    this.div_.style.height = "16px";
    if (this.color != null) 
        this.div_.style.backgroundColor = this.color;
    this.div_.style.left = c.x - (this.len_ / 2) + "px";
    this.div_.style.top = c.y - 55 + "px";
    this.div_.style.textAlign = "center"
    this.div_.innerHTML = this.msg_;
}
