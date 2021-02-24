setTimeout(() => loadAds(), 10);

async function loadAds() {
    let url = './my-ad';
    let response = await fetch(url);
    let paramAd = await response.json();
    if (paramAd.ads.length > 0) {
        for (let i = 0; i < paramAd.ads.length; i++) {
            let divColm = document.createElement("div");
            divColm.setAttribute("class", "col");
            let divCard = document.createElement("div");
            divCard.id = "ad_" + paramAd.ads[i][0];
            divCard.setAttribute("class", "card mb-4 shadow-sm");
            let divCardHeader = document.createElement("div");
            divCardHeader.setAttribute("class", "card-header");
            let h4 = document.createElement("h4");
            h4.setAttribute("class", "my-0 fw-normal");
            h4.appendChild(document.createTextNode(paramAd.ads[i][3] + " " + paramAd.ads[i][4]));
            divCardHeader.appendChild(h4);
            divCard.appendChild(divCardHeader);
            let divBody = document.createElement("div");
            divBody.setAttribute("class", "card-body");
            let img = document.createElement("img");
            if (paramAd.ads[i][5] !== null) {
                img.src = "images/" + paramAd.ads[i][5];
            } else {
                img.src = "images/no_image.png";
            }
            img.style = "width: 90%";
            divBody.appendChild(img);
            let h3 = document.createElement("h3");
            h3.setAttribute("class", "card-title pricing-card-title");
            h3.appendChild(document.createTextNode(new Intl.NumberFormat('ru-RU').format(paramAd.ads[i][1])));
            divBody.appendChild(h3);

            let label = document.createElement("label");
            label.name = "status";
            let b = document.createElement("b");
            b.innerText = "Status: ";
            label.appendChild(b);
            let select = document.createElement("select");
            select.name = "status";
            select.id = "status";
            label.appendChild(select);
            divBody.appendChild(label);
            let option1 = document.createElement("option");
            option1.value = paramAd.ads[i][2];
            option1.text = paramAd.ads[i][2];
            select.appendChild(option1);
            let option2 = document.createElement("option");
            if (paramAd.ads[i][2] === "For sale") {
                option2.value = "Sold";
                option2.text = "Sold";
            } else {
                option2.value = "For sale";
                option2.text = "For sale";
            }
            select.appendChild(option2);
            label.appendChild(document.createElement("br"));
            label.appendChild(document.createElement("br"));


            let button = document.createElement("button");
            button.type = "button";
            button.id = "ad_" + paramAd.ads[i][0];
            button.setAttribute("class", "w-100 btn btn-lg btn-primary");
            button.appendChild(document.createTextNode("Change"));
            divBody.appendChild(button);
            divCard.appendChild(divBody);
            divColm.appendChild(divCard);
            row.appendChild(divColm);
        }
    }
}

async function changeAdStatus(event) {
    let eventId = event.target.className;
    if (eventId === "w-100 btn btn-lg btn-primary") {
        let id = event.target.getAttribute('id').split("_")[1];
        let status = document.getElementById('status').value;
        const response = await fetch('./my-ad', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({id: id, status: status})
        });
        const json = await response.json();
    }
}