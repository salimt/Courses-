/*Name this external file gallery.js*/

function upDate(previewPic){
    var imgElem = document.getElementById("image");
    imgElem.style.backgroundImage = "url("+previewPic.src+")";
    imgElem.innerHTML = previewPic.alt;
}

function unDo(){
    var imgElem = document.getElementById("image");
    imgElem.style.backgroundImage = "url('')";
    imgElem.innerHTML = "Hover over an image below to display here.";
}