window.ining = window.ining || {};
ining.auth = ining.auth || {

    msg : "default",

    onLogin: function() {
        pwd = document.getElementById("password").value;
        pwd = sha1.hex_sha1(pwd);
        pwd = document.getElementById("code").value + pwd;
        pwd = sha1.hex_sha1(pwd);
        document.getElementById("password").value = pwd;
        document.getElementById("login").submit();
    }

}