<#import "components/macros.ftlh" as macros />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <@macros.header />
    <title>Bank</title>
</head>
<body onload="fetchProductUnits([<#list pagedResponse.content as content>
        {
        innerId: '${content.innerId}',
        unit: ${content.unit}
        },
</#list>])">
<@macros.navbar activeIndex=3 />
<h2>Products</h2>
<label for="country-select">Select a country to filter: </label>
<div class="container d-flex flex-row my-4">
    <select class="form-select mx-2" id="country-select">
        <option value="" <#if country?? == false>selected</#if>>---</option>
        <#list countries as count>
            <option value="${count}" <#if (country!'') == count>selected</#if>>
                ${count?upper_case}
            </option>
        </#list>
    </select>
    <button class="btn btn-primary mx-2"
            onclick="const x = getSelectedCountry(); location.href = '/central/admin/product?country='+x;"> Filter </button>
</div>
<div class="table-responsive">
    <table class="table table-primary table-hover">
        <tr>
            <th>#ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Seller</th>
            <th>Product Catalog Code</th>
        </tr>
        <#list pagedResponse.content as product>
            <tr id="${product.innerId}">
                <td>${product.innerId}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>
                    <span>${product.count}</span>
                    <span class="unit"></span> <span>left</span>
                </td>
                <td>${product.sellerId}</td>
                <td>${product.code}</td>
            </tr>
        </#list>
    </table>
</div>

<div class="footer">

    <a href="<@macros.spring.url '/admin/product?page=${pagedResponse.currentPage-1}'/>"
       class="btn btn-primary <#if pagedResponse.currentPage lte 1>disabled</#if>"> &lt; </a>
    <span>${pagedResponse.currentPage}</span>
    <a href="<@macros.spring.url '/admin/product?page=${pagedResponse.currentPage+1}'/>"
       class="btn btn-primary <#if pagedResponse.currentPage gte pagedResponse.totalPages>disabled</#if>"> &gt; </a>
</div>
<script src="<@macros.spring.url '/js/product.js' />"></script>
</body>
</html>