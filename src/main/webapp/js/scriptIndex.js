setTimeout(() => loadIndex(), 10);

async function loadIndex() {
    let url = './add';
    let response = await fetch(url);
    let paramAd = await response.json();
    let brand = paramAd.Brand;
    let divAll = document.createElement("div");
    divAll.setAttribute("class", "form-check form-check-inline");
    let inputAll = document.createElement("input");
    inputAll.setAttribute("class", "form-check-input");
    inputAll.type = "radio";
    inputAll.name = "brand";
    inputAll.value = 0 + "_" + "All";
    inputAll.checked = true;
    divAll.appendChild(inputAll);
    let labelAll = document.createElement("label");
    labelAll.setAttribute("class", "form-check-label");
    labelAll.name = "brand";
    labelAll.appendChild(document.createTextNode("All"));
    divAll.appendChild(labelAll);
    document.getElementById("brand").appendChild(divAll);
    brand.forEach(function (item) {
        let div = document.createElement("div");
        div.setAttribute("class", "form-check form-check-inline");
        let input = document.createElement("input");
        input.setAttribute("class", "form-check-input");
        input.type = "radio";
        input.name = "brand";
        input.value = item.id + "_" + item.name;
        div.appendChild(input);
        let label = document.createElement("label");
        label.setAttribute("class", "form-check-label");
        label.name = "brand";
        label.appendChild(document.createTextNode(item.name));
        div.appendChild(label);
        document.getElementById("brand").appendChild(div);
    });
    await loadAds();
}

async function selectFilter() {
    let previous = document.getElementsByName("previous")[0];
    previous.id = "previous_1_0";
    await loadAds();
}

async function loadAds() {
    let getPage = document.getElementsByName("previous");
    let page;
    if (getPage[0].id.split("_")[2] === 0) {
        page = 1;
    } else {
        page = Number(getPage[0].id.split("_")[2]) + 1;
    }
    let date = document.querySelector('input[name="date"]:checked').value;
    let photo = document.getElementById("photo").checked;
    let brand = document.querySelector('input[name="brand"]:checked').value.split("_", 2)[1];
    const response = await fetch('./index', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({date: date, photo: photo, brand: brand, page: page})
    });
    const json = await response.json();
    let element = document.getElementById("row");
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
    let maxId = 0;
    if (json.ads.length > 0) {
        for (let i = 0; i < json.ads.length; i++) {
            if (json.ads[i][0] > maxId) {
                maxId = json.ads[i][0];
            }
            let row = document.getElementById('row');
            let divColm = document.createElement("div");
            divColm.setAttribute("class", "col");
            let divCard = document.createElement("div");
            // divCard.id = "ad_" + json.ads[i][0];
            divCard.setAttribute("class", "card mb-4 shadow-sm");
            let divCardHeader = document.createElement("div");
            divCardHeader.setAttribute("class", "card-header");
            let h4 = document.createElement("h4");
            h4.setAttribute("class", "my-0 fw-normal");
            h4.appendChild(document.createTextNode(json.ads[i][3] + " " + json.ads[i][4]));
            divCardHeader.appendChild(h4);
            divCard.appendChild(divCardHeader);
            let divBody = document.createElement("div");
            divBody.setAttribute("class", "card-body");
            let img = document.createElement("img");
            if (json.ads[i][5] !== null) {
                img.src = "images/" + json.ads[i][5];
            } else {
                img.src = "images/no_image.png";
            }
            img.style = "width: 90%";
            divBody.appendChild(img);
            let h3 = document.createElement("h3");
            h3.setAttribute("class", "card-title pricing-card-title");
            h3.appendChild(document.createTextNode(new Intl.NumberFormat('ru-RU').format(json.ads[i][1])));
            divBody.appendChild(h3);
            let button = document.createElement("button");
            button.type = "button";
            button.id = "ad_" + json.ads[i][0];
            button.setAttribute("class", "w-100 btn btn-lg btn-primary");
            button.appendChild(document.createTextNode("View"));
            divBody.appendChild(button);
            divCard.appendChild(divBody);
            divColm.appendChild(divCard);
            row.appendChild(divColm);
        }
    }
    let previous = document.getElementsByName("previous")[0];
    let pageNumber = previous.id.split("_")[1];
    // let idAds = previous.id.split("_")[2];
    previous.id = "previous_" + pageNumber + "_" + maxId;
    if (json.ads.length < 6) {
        document.getElementsByName("next")[0].style.display = "none"
    } else {
        document.getElementsByName("next")[0].style.display = "block"
    }
    if (Number(document.getElementsByName("previous")[0].id.split("_")[1]) === 1) {
        document.getElementsByName("previous")[0].style.display = "none"
    } else {
        document.getElementsByName("previous")[0].style.display = "block"
    }
}

async function nextPage() {
    let previous = document.getElementsByName("previous")[0];
    let pageNumber = Number(previous.id.split("_")[1]) + 1;
    let idAds = previous.id.split("_")[2];
    previous.id = "previous_" + pageNumber + "_" + idAds;
    await loadAds();
}

async function previousPage() {
    let previous = document.getElementsByName("previous")[0];
    let pageNumber = Number(previous.id.split("_")[1]) - 1;
    let idAds = Number(previous.id.split("_")[2]) - 6;
    previous.id = "previous_" + pageNumber + "_" + idAds;
    await loadAds();
}

async function loadViewAd(event) {
    let eventId = event.target.className;
    if (eventId === "w-100 btn btn-lg btn-primary") {
        let id = event.target.getAttribute('id').split("_")[1];
        let url = './index?id=' + id;
        let response = await fetch(url);
        let paramAd = await response.json();
        document.getElementById("model-title")
            .appendChild(document.createTextNode(paramAd.ad.Brand + " " + paramAd.ad.Model));
        document.getElementById("price-title")
            .appendChild(document.createTextNode(new Intl.NumberFormat('ru-RU').format(paramAd.ad.Price)));
        document.getElementById("color")
            .appendChild(document.createTextNode("Color: " + paramAd.ad.Color));
        document.getElementById("mileage")
            .appendChild(document.createTextNode("Mileage: " + paramAd.ad.Mileage + " km"));
        document.getElementById("year")
            .appendChild(document.createTextNode("Year: " + paramAd.ad.Year));
        document.getElementById("body")
            .appendChild(document.createTextNode("Body: " + paramAd.ad.Body));
        document.getElementById("engine")
            .appendChild(document.createTextNode("Engine: " + paramAd.ad.Engine));
        document.getElementById("rudder")
            .appendChild(document.createTextNode("Rudder: " + paramAd.ad.Rudder));
        document.getElementById("transmission")
            .appendChild(document.createTextNode("Transmission: " + paramAd.ad.Transmission));
        document.getElementById("status")
            .appendChild(document.createTextNode("Status: " + paramAd.ad.Status));
        document.getElementById("phone")
            .appendChild(document.createTextNode("Phone: " + paramAd.ad.Phone));
        if (paramAd.ad.Photo === "") {
            paramAd.ad.Photo = "no_image.png,";
        }
        paramAd.ad.Photo.split(",").forEach(function (item, index) {
            let div = document.createElement("div");
            div.setAttribute("class", "carousel-item");
            if (index === 0) {
                div.setAttribute("class", "carousel-item active");
            }
            let img = document.createElement("img");
            img.src = "images/" + item;
            img.setAttribute("class", "d-block w-100");
            div.appendChild(img);
            document.getElementById("images-ad").appendChild(div);
        });
        document.querySelector('#active-view').click();
    }
}