function openNav(){
    document.getElementById("sideNav").style.width =
    "250px";
    document.getElementById("main").style.marginleft="250px";
}

function closeNav() {
    document.getElementById("sideNav").style.width = "0";
    document.getElementsByTagName("main").style.marginleft="0";
}
