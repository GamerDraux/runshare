let navClosed = true;

function openNav(){
    if (navClosed===true)
    {document.getElementById("sideNav").style.width =
    "250px";
    document.getElementById("main").style
    .marginLeft="250px";
    navClosed = false
    } else{
        closeNav();
    }
}

function closeNav() {
    document.getElementById("sideNav").style.width = "0";
    document.getElementById("main").style
    .marginLeft="0";
    navClosed= true;
}
