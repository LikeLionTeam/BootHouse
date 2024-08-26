export function initMap() {
    fetch('/api/naver/maps-script')
        .then(response => response.text())
        .then(scriptUrl => {
            const scriptElement = document.createElement('script');
            scriptElement.src = scriptUrl.trim();
            document.body.appendChild(scriptElement);
            scriptElement.onload = loadMap;
        });
}

function loadMap() {
    if (window.addresses && window.addresses.length > 0) {
        const x = parseFloat(window.addresses[0].x);
        const y = parseFloat(window.addresses[0].y);
        const location = new naver.maps.LatLng(y, x);

        const map = new naver.maps.Map('map', {
            center: location,
            zoom: 15
        });

        const marker = new naver.maps.Marker({
            map: map,
            position: location
        });

        const infowindow = new naver.maps.InfoWindow({
            content: `
                <div class="iw_inner custom_info_window">
                    <h3>${window.courseName}</h3>
                    <p>${window.courseLocation}</p>
                </div>`
        });

        naver.maps.Event.addListener(marker, "click", () => {
            if (infowindow.getMap()) {
                infowindow.close();
            } else {
                infowindow.open(map, marker);
            }
        });

        infowindow.open(map, marker);
    }
}