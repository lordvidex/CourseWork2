interface ProductInterface {
    productid: string;
    count: number;
    code: string;
    name: string;
    price: number;
    unit: number;
}

interface OrganizationInterface {
    innerid: string;
    name: string;
    address: string;
    country_code: string;
    products: [string];
}

async function getProducts(el: HTMLElement, ids: string[]) {
    // check if button is open then close without calling fetch else call fetch
    const hiddenRow: HTMLElement = <HTMLElement>el.parentElement.nextElementSibling;

    // change button to loading button
    const button: HTMLButtonElement = <HTMLButtonElement>el.getElementsByClassName('btn')[0];
    button.disabled = true;
    button.innerHTML = `<span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
Loading...`;

    if (hiddenRow.style.display.length !== 0) {
        // simply close by calling dependent function
        button.disabled = false;
        // @ts-ignore
        toggleShow(el);
        return null;
    }

    // @ts-ignore
    const requests = await Promise.all(ids.map(id => fetch(`/central/product/${id}`)))
    const jsonResult: ProductInterface[] = []
    for (const req of requests) {
        jsonResult.push(await req.json());
    }

    const table: HTMLTableElement = <HTMLTableElement>hiddenRow.getElementsByClassName('table')[0];
    // empty the rows if there are data
    if (table.rows.length !== 0) {
        table.innerHTML = "";
    }
    const headers = ['#ID', 'Name', 'Code', 'Price', 'Count']
    insertProductHeader(table, headers);
    insertProductItems(table,jsonResult);

    // hide loading spinner
    button.disabled = false;
    // @ts-ignore
    toggleShow(el);
}

function insertProductHeader(table: HTMLTableElement, headers: string[]) {
    const row = table.insertRow(-1);
    row.innerHTML = ''
    for (const head of headers) {
        row.innerHTML += '<th>'+head+'</th>';
    }
}

function insertProductItems(table: HTMLTableElement, items: ProductInterface[]) {
    for (const req of items) {
        // append a new row
        const row = table.insertRow(-1);
        row.id = `${req.productid}`;
        const cell1 = row.insertCell(-1);
        cell1.innerHTML = req.productid
        const cell2 = row.insertCell(-1);
        cell2.innerHTML = req.name;
        const cell3 = row.insertCell(-1);
        cell3.innerHTML = req.code;
        const cell4 = row.insertCell(-1);
        cell4.innerHTML = `${req.price}`;
        const cell5 = row.insertCell(-1);
        cell5.innerHTML = `${req.count} <span class="unit"></span> left`
    }
    // @ts-ignore
    fetchProductUnits(items.map(it => ({innerId: it.productid, unit: it.unit})))
}

// @ts-ignore
async function getOrganizationFrom(country: string) {
    console.log('Country clicked')
    if (country != null) {
        const response = await fetch(`/central/admin/organization-ajax?country=${country}`);
        const result: [OrganizationInterface] = await response.json();
        fillOrganizationTable(result);
    }
}

function fillOrganizationTable(data: [OrganizationInterface]) {
    const table = <HTMLTableElement> document.getElementById("OrganizationTable");
    // remove old elements except the header
    while(table.rows.length != 0) {
        table.deleteRow(-1);
    }
    // insert header
    const header = table.insertRow(-1);
    header.innerHTML = `<th></th>
            <th>#ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>Country Code</th>`;
    // insert new elements
    for (let i = 0; i < data.length; i++) {
        const cell = table.insertRow(-1);
        const products = '['+data[i].products.map(each => `'${each}'`).join(',')+']';
        cell.innerHTML = `<td onclick="getProducts(this, ${products})">
                    <button class="btn btn-primary">&downarrow;</button>
                </td>
                <td>${data[i].innerid}</td>
                <td>${data[i].name}</td>
                <td>${data[i].address}</td>
                <td>${data[i].country_code}</td>`
        // hidden cell
        const hidden = table.insertRow(-1);
        hidden.classList.add("hidden-details");
        hidden.innerHTML = `<td colspan="5">
                    <h3>Products</h3>
                    <table class="table table-striped table-info w-75">
                    </table>
                </td>`
    }
}