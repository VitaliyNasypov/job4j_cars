setTimeout(() => loadAddNewAd(), 10);

async function loadAddNewAd() {
    let url = './add';
    let response = await fetch(url);
    let paramAd = await response.json();
    let brand = paramAd.Brand;
    let model = paramAd.Model;
    let engine = paramAd.Engine;
    let transmission = paramAd.Transmission;
    let body = paramAd.Body;
    let rudder = paramAd.Rudder;
    let labelEngine = document.createElement("label");
    labelEngine.name = "engine";
    let b = document.createElement("b");
    b.innerText = "Engine type:  ";
    labelEngine.appendChild(b);
    engine.forEach(function (item) {
        let input = document.createElement("input");
        input.type = "radio";
        input.name = "engine";
        input.value = item.id + "_" + item.type;
        input.checked = true;
        labelEngine.appendChild(input);
        labelEngine.appendChild(document.createTextNode(" " + item.type + "  "));
    });
    document.getElementById('select-engine').appendChild(labelEngine);
    addInputRadio(transmission, "select-transmission", "transmission", "Transmission: ");
    addInputRadio(body, "select-body", "body", "Body: ");
    addInputRadio(rudder, "select-rudder", "rudder", "Rudder: ");
    addInputSelect(brand, 'select-brand', "brand", "Brand: ", 0);
    addInputSelect(model, 'select-model', "model", "Model: ", brand[0].id);
}

function addInputSelect(arrayParam, divID, name, header, idBrand) {
    let label = document.createElement("label");
    label.name = name;
    let b = document.createElement("b");
    b.innerText = header;
    label.appendChild(b);
    let select = document.createElement("select");
    select.name = name;
    select.id = name;
    arrayParam.forEach(function (item) {
        if (idBrand !== 0 && item.brand.id === idBrand) {
            let option = document.createElement("option");
            option.value = item.id + "_" + item.name;
            option.text = item.name;
            select.appendChild(option);
        } else if (idBrand === 0) {
            let option = document.createElement("option");
            option.value = item.id + "_" + item.name;
            option.text = item.name;
            select.appendChild(option);
        }
    });
    label.appendChild(select);
    document.getElementById(divID).appendChild(label);
}

function addInputRadio(arrayParam, divID, name, header) {
    let label = document.createElement("label");
    label.name = name;
    let b = document.createElement("b");
    b.innerText = header;
    label.appendChild(b);
    arrayParam.forEach(function (item) {
        let input = document.createElement("input");
        input.type = "radio";
        input.name = name;
        input.value = item.id + "_" + item.name;
        input.checked = true;
        label.appendChild(input);
        label.appendChild(document.createTextNode(" " + item.name + "  "));
    });
    document.getElementById(divID).appendChild(label);
}

async function OnSelectionChange(select) {
    let brandId = document.getElementById('brand').value.split("_", 2)[0];
    let modelId = document.getElementById('model');
    for (let i = 0; i < modelId.length; i++) {
        modelId.remove(i);
    }
    let url = './add';
    let response = await fetch(url);
    let paramAd = await response.json();
    let model = paramAd.Model;
    model.forEach(function (item, index) {
        if (Number(item.brand.id) === Number(brandId)) {
            let option = document.createElement("option");
            option.value = item.id + "_" + item.name;
            option.text = item.name;
            document.getElementById('model').appendChild(option);
        }
    });
}