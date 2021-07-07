var modal1 = document.getElementById('modal1');

// Get the button that opens the modal
var btn1 = document.getElementById("btn-modal1");

// Get the <span> element that closes the modal
var span1 = document.getElementById("close1");

// When the user clicks on the button, open the modal
btn1.onclick = function() {
    modal1.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span1.onclick = function() {
    modal1.style.display = "none";
}
window.onclick = function(event) {
    if (event.target === modal1) {
        modal1.style.display = "none";
    }
    if (event.target === modal2) {
        modal2.style.display = "none";
    }
}
if (document.getElementById('modal2') != null) {

    var modal2 = document.getElementById('modal2');

// Get the button that opens the modal
    var btn2 = document.getElementById("btn-modal2");

// Get the <span> element that closes the modal
    var span2 = document.getElementById("close2");

// When the user clicks on the button, open the modal
    btn2.onclick = function () {
        modal2.style.display = "block";
    }

// When the user clicks on <span> (x), close the modal
    span2.onclick = function () {
        modal2.style.display = "none";
    }
}