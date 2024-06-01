const global_url = "http://localhost:8080";
var loggedInUser = {};
$(document).ready(function() {
    const activeTab = $.cookie("activeTab");
    if (activeTab == null || activeTab == '') {
        $(".mainBody").load('home.html');
    } else if (activeTab == 'products') {
        getRequest('/products', 'products');
    } else {
        $(".mainBody").load(activeTab + '.html');
    }

});
$(window).on("load", function() {
    const url = global_url + "/sessionCheckOk";
    $.ajax({
        type: "GET",
        url: url,
        xhrFields: {
            withCredentials: true,
            "SameSite": "None",
            "secure": true // Send cookies with the request
        },
        beforeSend: function(request) {
            request.setRequestHeader('session', $.cookie("session"));
            openPopup('loader');
        },
        success: function(resp) {
            $("#backDrop").prop('hidden', true);
            postLogin(resp);
        },
        error: function(error) {
            $("#backDrop").prop('hidden', true);
            console.log(error)
        }
    })
})

$("#homeBtn").click(function() {
    $.cookie("activeTab", "home");
    $(".mainBody").load('home.html');
});
$("#aboutBtn").click(function() {
    $.cookie("activeTab", "home");
    $(".mainBody").load('home.html', function() {
        window.location.href = "index.html#aboutUsSection";
    });
    return true;
});
$(".productsBtn").click(function() {
    $.cookie("activeTab", "home");
    $(".mainBody").load('home.html', function() {
        window.location.href = "index.html#productsSection";
    });
});
$("#faqBtn").click(function() {
    $.cookie("activeTab", "home");
    $(".mainBody").load('home.html', function() {
        window.location.href = "index.html#faqSection";
    });
});


function search(a) {

    $(".popup-bg").css({ "display": "block" });

    let searchText = "";
    searchText = ($(a).val()).toUpperCase();
    let html = "";
    const ar = ['plants', 'vase', 'plant care products', 'products', 'about', 'faq'];
    for (let i in ar) {
        if (searchText != '' && searchText != null && (ar[i].toUpperCase()).includes(searchText)) {
            html = html + "<li class='clickable-list' onclick='autoFill(this)'> " + ar[i] + " </li>";

        }
    }
    if (html == null || html == '') {
        $(".popup-bg").css({ "display": "none" });
    }
    $("#navSearch").html(html);
    console.log(html);

}

function openPopup(popUpType) {
    $("#backDrop").prop('hidden', false);
    if (popUpType === 'login') {
        $("#backdropContent").load('login.html');
    } else if (popUpType == 'signUp') {
        $("#backdropContent").load('signUp.html');
    } else if (popUpType == 'loader') {
        $("#backdropContent").load('loader.html');
    }
}
$("#backDrop > .row").click(function(e) {
    if (e.target === e.currentTarget) {
        $("#backDrop").prop('hidden', true);
    }

})

function autoFill(a) {
    console.log($(a).text());
    $("#searchBox").val($(a).text());
}
$("#navSearchBtn").click(function() {

    switch (($("#searchBox").val()).trim()) {
        case "products":

            window.location.href = "index.html#productsSection";
            break;
        case "plants":

            window.location.href = "index.html#productsSection";
            break;
        case "about":
            window.location.href = "index.html#aboutUsSection";
            break;
        case "vase":
            window.location.href = "index.html#aboutUsSection";
            break;
        case "plant care products":
            window.location.href = "index.html#aboutUsSection";
            break;
        case "faq":
            window.location.href = "index.html#faqSection";
            break;

        default:
            window.location.href = "index.html#mainBg";


    }
})

function loginUser() {
    var body = $("#loginForm").serializeArray();
    var request = {};
    for (const args in body) {
        var key = body[args].name;
        var value = body[args].value;
        request[key] = value;

    }

    postRequest(global_url + "/login", request, 'login');

}

function postLogin(resp) {
    if (resp == 'loggedOut') {
        location.reload();
    } else {
        loggedInUser = resp;
        $("#backDrop").prop('hidden', true);
        const html = '<li class="nav-item dropdown"> ' +
            '<a id="profileBtn" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" ' +
            'role="button" data-bs-toggle="dropdown" aria-expanded="false">' + resp.name + '</a> ' +
            '<ul class="dropdown-menu" aria-labelledby="navbarDropdown"> ' +
            '<li><a id="profileView" class="dropdown-item" href="#">Profile</a></li> ' +
            '<li><a onclick="openCart()" id="cart" class="dropdown-item" href="#">Cart</a></li> ' +
            '<li> ' +
            '<hr class="dropdown-divider"></li> ' +
            "<li><a id='logout' onclick='logout()' class='dropdown-item' href='#'>Logout</a></li></ul></li>";

        $("#loginToggleSection").html("<img class='profileImg me-2' src='/static/images/users/userx.png' alt='userx'>" + html);
        $("#loginToggleSection").css({ "display": "flex" });
        const activeTab = $.cookie("activeTab");
        if (activeTab == null || activeTab == '') {
            $(".mainBody").load('home.html');
        } else if (activeTab == 'products') {
            getRequest('/products', 'products');
        } else {
            $(".mainBody").load(activeTab + '.html');
        }
    }



}

function openProfile() {
    $("#backDrop").prop('hidden', false);
    $("#backdropContent").load('signUp.html');


}

function openCart() {
    $("#backDrop").prop('hidden', false);
    $("#backdropContent").load('cart.html', function() {
        var cartProducts = loggedInUser['cart'].products;
        var html = '';
        let counter = 1;
        for (let i in cartProducts) {
            let path = ((cartProducts[i].path != null || cartProducts[i].path != '') ? "/static/images/" + cartProducts[i].path : "/static/images/products/noProducts.png");
            html = html + '<tr id="cartProduct_' + cartProducts[i].productId + '"><th scope="row">' + counter + '</th> ' +
                '<td style="width:8vw; height:10vh;"> ' +
                '<img class="img-fluid" src="' + path + '"> ' +
                '<div class="btn-group" role="group" aria-label="Basic example"> ' +
                '<button onclick="decreaseQty(' + cartProducts[i].productId + ')" type="button" class="btn btn-secondary"> - </button> ' +
                '<input style="width:30px;" id="ProductQty_' + cartProducts[i].productId + '" disabled type="number" min="1" max="" step="1" value="' + cartProducts[i].qty + '"> ' +
                '<button onclick="increaseQty(' + cartProducts[i].productId + ')" type="button" class="btn btn-primary"> + </button> ' +
                '</div></td><td><h3 class="productName" id="product_' + cartProducts[i].productId + '">' + cartProducts[i].productName + '</h3> ' +
                '<p class="cartProductPrice" id="price_' + cartProducts[i].productId + '">' + cartProducts[i].price + ' Rs.</p></td><td> ' +
                '<button onclick="updateCartItem(' + cartProducts[i].productId + ')" disabled id="updateCartItem_' + cartProducts[i].productId + '" type="button" class="btn btn-primary"> Update </button> ' +
                '<button onclick="placeOrder(' + cartProducts[i].productId + ')"  id="placeOrder_' + cartProducts[i].productId + '" type="button" class="btn btn-success"> Place Order </button> ' +
                '<button onclick="removeCartItem(' + cartProducts[i].productId + ')" id="removeCartItem_' + cartProducts[i].productId + '" type="button" class="btn btn-danger"> <i class="fa fa-trash"></i> </button> ' +
                '</td> </tr>';
            counter = counter + 1;
        }

        $("#cartProductsList").html(html);
    });
}

function toggleUpdateCart(id) {
    debugger;
    var productId = id;
    var value = $("#ProductQty_" + id).val();

    var prod = (loggedInUser['cart'].products).filter(function(abc) {
        if (abc.productId == productId) {
            return abc;
        }
    })
    if (parseInt(value) != prod[0].qty) {
        $("#updateCartItem_" + productId).prop("disabled", false);
    } else {
        debugger;
        $("#updateCartItem_" + productId).prop("disabled", true);
    }
}

function increaseQty(id) {
    var qty = parseInt($("#ProductQty_" + id).val());
    $("#ProductQty_" + id).val(qty + 1);
    toggleUpdateCart(id);
}

function decreaseQty(id) {
    var qty = parseInt($("#ProductQty_" + id).val());
    if (qty > 1) {
        $("#ProductQty_" + id).val(qty - 1);
        toggleUpdateCart(id);
    }
}

function removeCartItem(id) {
    var prod = (loggedInUser['cart'].products).filter(function(abc) {
        if (abc.productId == id) {
            return abc;
        }
    });
    var obj = prod[0];
    obj.status = 'INACTIVE';
    console.log(obj);
    var headers = { "key": "session", "value": $.cookie("session") };
    postRequestWithHeaders(global_url + "/updateCart", headers, obj, "removeCartItem");

}

function updateCartItem(id) {
    var prod = (loggedInUser['cart'].products).filter(function(abc) {
        if (abc.productId == id) {
            return abc;
        }
    });
    var obj = prod[0];
    obj.qty = $("#ProductQty_" + id).val();
    console.log(obj);
    var headers = { "key": "session", "value": $.cookie("session") };
    postRequestWithHeaders(global_url + "/updateCart", headers, obj, "updateCartItem");

}


function openProducts(resp) {


    $(".mainBody").load('products.html', function() {

        const plants = [];
        const vase = [];
        const pcp = []
        for (let i in resp) {
            if (resp[i].category == 'Plants') {
                plants.push(resp[i]);
            } else if (resp[i].category == 'Vase') {
                vase.push(resp[i]);
            } else if (resp[i].category == 'Plant Care Products') {
                pcp.push(resp[i]);
            } else {
                console.log(resp[i]);
            }
        }

        loadPlants(plants);
        loadVase(vase);
        loadPpc(pcp);

    });

    $.cookie("activeTab", "products")
        //var html='<h3 style="color:black;"><u>Plants</u></h3>'



}

function loadPlants(plants) {
    var html = '<h3 style="color:black;"><u>Plants</u></h3> ';
    var addToCartbtn = '<button disabled type="button" class="btn btn-danger">Add to cart</button> ';
    if (plants.length > 0) {
        for (let i in plants) {
            if (Object.keys(loggedInUser).length != 0) {
                const alreadyAdded = loggedInUser['cart'].products;

                var presentInCart = alreadyAdded.some(obj => obj.productId == plants[i].productId);
                if (presentInCart) {
                    addToCartbtn = '<button disabled type="button" class="btn btn-success"><i class="fa fa-check"></i>Added</button> ';
                } else {
                    addToCartbtn = '<button onclick="addToCart(this)" id="' + plants[i].productId + '" type="button" class="btn btn-danger">Add to cart</button> ';

                }
            } else {
                addToCartbtn = '<button onclick="addToCart(this)" id="' + plants[i].productId + '" type="button" class="btn btn-danger">Add to cart</button> ';
            }

            let path = ((plants[i].path != null || plants[i].path != '') ? "/static/images/" + plants[i].path : "/static/images/products/noProducts.png");
            html = html +
                '<div class="col-md-3"> ' +
                '<div class="col"> ' +
                '<div class="card" style="width: 18rem;"> ' +
                '<img src="' + path + '" class="card-img-top card-img-gen" alt="..."> ' +
                '<div class="card-body"> ' +
                '<h5 class="card-title">' + plants[i].productName + '</h5> ' +
                '<p class="card-text">Price: ' + plants[i].price + 'Rs.</p> ' +
                '<button type="button" class="btn btn-primary">Buy Now</button> ' +
                addToCartbtn +
                '</div> ' +
                '</div> ' +
                '</div> ' +
                '</div> ';
        }
    } else {
        html = html +
            '<div class="col-md-3"> ' +
            '<div class="col"> ' +
            '<div class="card" style="width: 18rem;"> ' +
            '<img src="/static/images/products/noProducts.png" class="card-img-top card-img-gen" alt="..."> ' +
            '<div class="card-body"> ' +
            '<h5 class="card-title"> No Product Found</h5> ' +
            '<p class="card-text">Price: 0.0Rs.</p> ' +
            '<button disabled type="button" class="btn btn-primary">Buy Now</button> ' +
            '<button disabled type="button" class="btn btn-danger">Add to cart</button> ' +
            '</div> ' +
            '</div> ' +
            '</div> ' +
            '</div> ';
    }

    $("#plantsSection").html(html);

}

function loadVase(vase) {
    var html = '<h3 style="color:black;"><u>Vases</u></h3> ';
    var addToCartbtn = '<button disabled type="button" class="btn btn-danger">Add to cart</button> ';
    if (vase.length > 0) {
        for (let i in vase) {
            if (Object.keys(loggedInUser).length != 0) {
                const alreadyAdded = loggedInUser['cart'].products;

                var presentInCart = alreadyAdded.some(obj => obj.productId == vase[i].productId);
                if (presentInCart) {
                    addToCartbtn = '<button disabled type="button" class="btn btn-success"><i class="fa fa-check"></i>Added</button> ';
                } else {
                    addToCartbtn = '<button onclick="addToCart(this)" id="' + vase[i].productId + '" type="button" class="btn btn-danger">Add to cart</button> ';
                }
            } else {
                addToCartbtn = '<button onclick="addToCart(this)" id="' + vase[i].productId + '" type="button" class="btn btn-danger">Add to cart</button> ';
            }
            let path = ((vase[i].path != null || vase[i].path != '') ? "/static/images/" + vase[i].path : "/static/images/products/noProduct");
            html = html +
                '<div class="col-md-3"> ' +
                '<div class="col"> ' +
                '<div class="card" style="width: 18rem;"> ' +
                '<img src="' + path + '" class="card-img-top card-img-gen" alt="..."> ' +
                '<div class="card-body"> ' +
                '<h5 class="card-title">' + vase[i].productName + '</h5> ' +
                '<p class="card-text">Price: ' + vase[i].price + 'Rs.</p> ' +
                '<button type="button" class="btn btn-primary">Buy Now</button> ' +
                addToCartbtn +
                '</div> ' +
                '</div> ' +
                '</div> ' +
                '</div> ';
        }
    } else {
        html = html +
            '<div class="col-md-3"> ' +
            '<div class="col"> ' +
            '<div class="card" style="width: 18rem;"> ' +
            '<img src="/static/images/products/noProducts.png" class="card-img-top card-img-gen" alt="..."> ' +
            '<div class="card-body"> ' +
            '<h5 class="card-title"> No Product Found</h5> ' +
            '<p class="card-text">Price: 0.0Rs.</p> ' +
            '<button disabled type="button" class="btn btn-primary">Buy Now</button> ' +
            '<button disabled type="button" class="btn btn-danger">Add to cart</button> ' +
            '</div> ' +
            '</div> ' +
            '</div> ' +
            '</div> ';
    }

    $("#vaseSection").html(html);
}

function loadPpc(ppc) {
    var html = '<h3 style="color:black;"><u>Plant Care Products</u></h3> ';
    var addToCartbtn = '<button disabled type="button" class="btn btn-danger">Add to cart</button> ';
    if (ppc.length > 0) {
        for (let i in ppc) {
            if (Object.keys(loggedInUser).length != 0) {
                const alreadyAdded = loggedInUser['cart'].products;

                var presentInCart = alreadyAdded.some(obj => obj.productId == ppc[i].productId);
                if (presentInCart) {
                    addToCartbtn = '<button disabled type="button" class="btn btn-success"><i class="fa fa-check"></i>Added</button> ';
                } else {
                    addToCartbtn = '<button onclick="addToCart(this)" id="' + ppc[i].productId + '" type="button" class="btn btn-danger">Add to cart</button> ';
                }
            } else {
                addToCartbtn = '<button onclick="addToCart(this)" id="' + ppc[i].productId + '" type="button" class="btn btn-danger">Add to cart</button> ';
            }
            let path = ((ppc[i].path != null || ppc[i].path != '') ? "/static/images/" + ppc[i].path : "/static/images/products/noProduct");
            html = html +
                '<div class="col-md-3"> ' +
                '<div class="col"> ' +
                '<div class="card" style="width: 18rem;"> ' +
                '<img src="' + path + '" class="card-img-top card-img-gen" alt="..."> ' +
                '<div class="card-body"> ' +
                '<h5 class="card-title">' + ppc[i].productName + '</h5> ' +
                '<p class="card-text">Price: ' + ppc[i].price + 'Rs.</p> ' +
                '<button type="button" class="btn btn-primary">Buy Now</button> ' +
                '<button onclick="addToCart(this)" id="' + ppc[i].productId + '" type="button" class="btn btn-danger">Add to cart</button> ' +
                '</div> ' +
                '</div> ' +
                '</div> ' +
                '</div> ';
        }
    } else {
        html = html +
            '<div class="col-md-3"> ' +
            '<div class="col"> ' +
            '<div class="card" style="width: 18rem;"> ' +
            '<img src="/static/images/products/noProducts.png" class="card-img-top card-img-gen" alt="..."> ' +
            '<div class="card-body"> ' +
            '<h5 class="card-title"> No Product Found</h5> ' +
            '<p class="card-text">Price: 0.0Rs.</p> ' +
            '<button disabled type="button" class="btn btn-primary">Buy Now</button> ' +
            '<button disabled type="button" class="btn btn-danger">Add to cart</button> ' +
            '</div> ' +
            '</div> ' +
            '</div> ' +
            '</div> ';
    }
    $("#plantCareProductsSection").html(html);
}

function addToCart(obj) {
    // var request = { "productId": obj.id, "session": $.cookie("session") };
    var url = global_url + "/addToCart/" + obj.id;
    console.log(url);
    debugger;
    if ($.cookie("session") != null && $.cookie("session") != '') {

        $.ajax({
            type: "GET",
            url: url,
            xhrFields: {
                withCredentials: true,
                "SameSite": "None",
                "secure": true // Send cookies with the request
            },
            beforeSend: function(request) {
                request.setRequestHeader('session', $.cookie("session"));
                openPopup('loader');
            },
            success: function(resp) {
                $("#backDrop").prop('hidden', true);
                console.log(resp.msg);
                debugger;
                if (resp.msg == "addedToCart") {
                    debugger;
                    $.cookie("activeTab", "products");
                    console.log(resp);
                    location.reload();

                } else {
                    debugger;
                    console.log(resp);

                }
            },
            error: function(error) {
                debugger;
                console.log(error)
            }
        });
    } else {
        openPopup("login");
    }

}

function logout() {

    const url = global_url + "/logout";
    $.ajax({
        type: "GET",
        url: url,
        xhrFields: {
            withCredentials: true,
            "SameSite": "None",
            "secure": true // Send cookies with the request
        },
        beforeSend: function(request) {
            request.setRequestHeader('session', $.cookie("session"));
            openPopup('loader');
        },
        success: function(resp) {

            console.log(resp);
            if (resp == "loggedOut") {
                $.removeCookie("activeTab");
                $.removeCookie("session");
                $.removeCookie("jwt");
                location.reload();

            } else {

                console.log(resp);
            }
        },
        error: function(error) {
            console.log(error)
            $.removeCookie("activeTab");
            $.removeCookie("session");
            $.removeCookie("jwt");
            location.reload();
        }
    })


}