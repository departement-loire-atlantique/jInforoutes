/* Déclenche la lecture ou l'arrêt du défilement des images de webcam
 * Les images de webcam défilent automatiquement pendant un cycle de rotation de 12 images
 * Le bouton "Activer / Désactiver"
 * 
 * */
let nbSecond = 3;
let maxCountImage = 5;
let imageCount = 0;
let isWebcamOn = false;

document.querySelectorAll('button[data-webcam-name]').forEach((buttonElement) => {
    buttonElement.addEventListener("click", this.playPauseWebcam);
});

function playPauseWebcam(event) {
    event.preventDefault();
    imageCount = 0;
    var webcamName = this.getAttribute("data-webcam-name");
    console.log("Play / pause. Webcam " + webcamName + " on ? : "+isWebcamOn);

    if(isWebcamOn){
    	this.textContent = "activer";
    	isWebcamOn = false;
    	console.log("Passage webcam " + webcamName + " off");
    }
    else{
    	this.textContent = "désactiver";
    	isWebcamOn = true;
    	console.log("Passage webcam " + webcamName + " on");
    	refreshLastImageWebCam(webcamName, this);
    }
    
    
    
}

function refreshLastImageWebCam(webcamName, boutonWebcam){
	console.log("Raffraichissement de la webcam : "+webcamName + " count : "+imageCount + " / bouton : "+boutonWebcam);
	now = new Date();
	if(isWebcamOn){
		var img = document.getElementById("imageWebcam"+webcamName);
		var srcUpdated = img.getAttribute("data-webcam-src")+"?timestamp=" + now.getTime();
		console.log(srcUpdated);
		img.setAttribute("src", srcUpdated);
		  
	 	imageCount++;
    	if(imageCount < maxCountImage){
    		console.log(imageCount + " / " + maxCountImage);
    		setTimeout(function(){refreshLastImageWebCam(webcamName, boutonWebcam)}, nbSecond*1000);
		}else{
			boutonWebcam.textContent = "activer";
		}
	}
}

function pauseWebcam(webcamName){
	var boutonWebcam = document.querySelector("button[data-webcam-name='"+valwebcamName+"']");
	console.log("Pause de la webcam : "+webcamName);
	if(isWebcamOn){
		boutonWebcam.textContent = "activer";
	}
	else{
		boutonWebcam.textContent = "désactiver";
	}
}



//setTimeout(function(){refreshLastImageWebCam(webcamName)}, 5000);

