window.ining = window.ining || {

    defLat: 55.085048,
    defLng: 38.757705,
    defZoom: 12,

    loadCar: 0,
    loadDate: null,
    loadFrom: null,
    loadTo: null,
    loadZones: null,

    setPosition: function(lat, lng, zoom)
    {
        this.defLat = lat;
        this.defLng = lng;
        this.defZoom = zoom;
        if (ining.gps && ining.gps.map != null) ining.gps.toCenter();
    },

    loadTrack: function(car, date, from, to, zones)
    {
        this.loadCar = car;
        this.loadDate = date;
        this.loadFrom = from;
        this.loadTo = to;
        this.loadZones = zones;
        if (ining.gps && ining.gps.map != null)
            ining.gps.loadCarTrack(ining.loadCar, ining.loadDate, ining.loadFrom, ining.loadTo, ining.loadZones);
    },

    load: function()
    {
        var head= document.getElementsByTagName('head')[0];
        var script= document.createElement('script');
        script.type= 'text/javascript';
        script.src= '/Resource?id=js/maplib.js';
        script.defer = "defer";
        script.onreadystatechange = function () {
            if (this.readyState == 'complete') ining.init();
        }
        script.onload = ining.init;
        head.appendChild(script);
    },

    init: function()
    {
        ining.gps.init();
        if (ining.geo) ining.geo.initialize();
    },

    done: function()
    {
        if (ining.gps) ining.gps.done();
    },

    resize: function()
    {
        if (ining.gps) ining.gps.resize();
    }

}