var sha1 = {

    hexcase : 0,
    b64pad : "",
    chrsz : 8,

    hex_sha1 : function(s) {
        return sha1.binb2hex(sha1.core_sha1(sha1.str2binb(s),s.length * sha1.chrsz));
    },

    b64_sha1 : function(s) {
        return sha1.binb2b64(sha1.core_sha1(sha1.str2binb(s),s.length * sha1.chrsz));
    },

    str_sha1 : function(s) {
        return sha1.binb2str(sha1.core_sha1(sha1.str2binb(s),s.length * sha1.chrsz));
    },

    hex_hmac_sha1 : function(key, data) {
        return sha1.binb2hex(sha1.core_hmac_sha1(key, data));
    },
    
    b64_hmac_sha1 : function(key, data) {
        return sha1.binb2b64(sha1.core_hmac_sha1(key, data));
    },

    str_hmac_sha1 : function(key, data) {
        return sha1.binb2str(sha1.core_hmac_sha1(key, data));
    },

    core_sha1 : function(x, len) {
        x[len >> 5] |= 0x80 << (24 - len % 32);
        x[((len + 64 >> 9) << 4) + 15] = len;
        var w = Array(80);
        var a =  1732584193;
        var b = -271733879;
        var c = -1732584194;
        var d =  271733878;
        var e = -1009589776;
        for (var i = 0; i < x.length; i += 16) {
            var olda = a;
            var oldb = b;
            var oldc = c;
            var oldd = d;
            var olde = e;
            for(var j = 0; j < 80; j++) {
                if(j < 16) w[j] = x[i + j];
                else w[j] = sha1.rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1);
                var t = sha1.safe_add(sha1.safe_add(sha1.rol(a, 5), sha1.sha1_ft(j, b, c, d)),
                    sha1.safe_add(sha1.safe_add(e, w[j]), sha1.sha1_kt(j)));
                e = d;
                d = c;
                c = sha1.rol(b, 30);
                b = a;
                a = t;
            }
            a = sha1.safe_add(a, olda);
            b = sha1.safe_add(b, oldb);
            c = sha1.safe_add(c, oldc);
            d = sha1.safe_add(d, oldd);
            e = sha1.safe_add(e, olde);
        }
        return Array(a, b, c, d, e);
    },

    sha1_ft : function(t, b, c, d) {
        if(t < 20) return (b & c) | ((~b) & d);
        if(t < 40) return b ^ c ^ d;
        if(t < 60) return (b & c) | (b & d) | (c & d);
        return b ^ c ^ d;
    },

    sha1_kt : function(t) {
        return (t < 20) ?  1518500249 : (t < 40) ?  1859775393 :
        (t < 60) ? -1894007588 : -899497514;
    },

    core_hmac_sha1 : function(key, data) {
        var bkey = sha1.str2binb(key);
        if (bkey.length > 16) bkey = sha1.core_sha1(bkey, key.length * sha1.chrsz);
        var ipad = Array(16), opad = Array(16);
        for (var i = 0; i < 16; i++) {
            ipad[i] = bkey[i] ^ 0x36363636;
            opad[i] = bkey[i] ^ 0x5C5C5C5C;
        }
        var hash = sha1.core_sha1(ipad.concat(sha1.str2binb(data)), 512 + data.length * sha1.chrsz);
        return sha1.core_sha1(opad.concat(hash), 512 + 160);
    },

    safe_add : function(x, y) {
        var lsw = (x & 0xFFFF) + (y & 0xFFFF);
        var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
        return (msw << 16) | (lsw & 0xFFFF);
    },

    rol : function(num, cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    },

    str2binb : function(str) {
        var bin = Array();
        var mask = (1 << sha1.chrsz) - 1;
        for (var i = 0; i < str.length * sha1.chrsz; i += sha1.chrsz)
            bin[i>>5] |= (str.charCodeAt(i / sha1.chrsz) & mask) << (32 - sha1.chrsz - i%32);
        return bin;
    },

    binb2str : function(bin) {
        var str = "";
        var mask = (1 << sha1.chrsz) - 1;
        for (var i = 0; i < bin.length * 32; i += sha1.chrsz)
            str += String.fromCharCode((bin[i>>5] >>> (32 - sha1.chrsz - i%32)) & mask);
        return str;
    },

    binb2hex : function(binarray) {
        var hex_tab = sha1.hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
        var str = "";
        for (var i = 0; i < binarray.length * 4; i++) {
            str += hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8+4)) & 0xF) +
            hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8  )) & 0xF);
        }
        return str;
    },

    binb2b64 : function(binarray) {
        var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        var str = "";
        for(var i = 0; i < binarray.length * 4; i += 3) {
            var triplet = (((binarray[i   >> 2] >> 8 * (3 -  i   %4)) & 0xFF) << 16)
            | (((binarray[i+1 >> 2] >> 8 * (3 - (i+1)%4)) & 0xFF) << 8 )
            |  ((binarray[i+2 >> 2] >> 8 * (3 - (i+2)%4)) & 0xFF);
            for(var j = 0; j < 4; j++) {
                if(i * 8 + j * 6 > binarray.length * 32) str += sha1.b64pad;
                else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
            }
        }
        return str;
    }

}