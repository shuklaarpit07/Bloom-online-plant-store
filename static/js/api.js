function postRequest(URL, body, fromFunc) {
    console.log(JSON.stringify(body));
    $.ajax({
        type: "POST",
        url: URL,
        data: JSON.stringify(body),
        contentType: 'application/json',
        dataType: 'json',
        headers: { "Accept": "application/json" },
        beforeSend: function() {
            openPopup('loader');
        },
        success: function(resp, status, xhr) {
            $("#backDrop").prop('hidden', true);
            if (fromFunc === 'login') {
                console.log(status);
                var specificHeader = xhr.getResponseHeader("Set-Cookie");
                var jwt = xhr.getResponseHeader("jwt");
                var session = xhr.getResponseHeader("session");
                $.cookie("jwt", jwt);
                $.cookie("session", session);

                debugger;
                console.log(specificHeader);
                console.log(jwt);
                postLogin(resp);
            } else {
                console.log(resp);
            }

        },
        error: function(error) {
            console.log(error);
        }
    });


}

function postRequestWithHeaders(url, headers, body, fromFunc) {
    var response = {};
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(body),
        contentType: 'application/json',
        dataType: 'json',
        headers: { "Accept": "application/json" },
        beforeSend: function(request) {
            request.setRequestHeader(headers['key'], headers['value']);
        },
        success: function(resp) {
            if (fromFunc == "removeCartItem") {
                $("#cartProduct_" + body.productId).remove();
                loggedInUser = resp;
            } else if (fromFunc == "updateCartItem") {
                $("#updateCartItem_" + body.productId).prop("disabled", true);
                loggedInUser = resp;
            }

        },
        error: function(error) {
            response = error;
        }
    });
    return response;

}

function getRequest(url, fromFunc) {

    debugger;
    $.ajax({
        type: "GET",
        url: global_url + url,
        beforeSend: function() {
            openPopup('loader');
        },
        success: function(resp) {
            console.log(resp);
            $("#backDrop").prop('hidden', true);
            if (fromFunc === 'sessionCheck') {
                debugger;
                postLogin(resp);
            } else if (fromFunc == 'products') {
                openProducts(resp);
            } else {
                console.log(resp);
            }
        },
        error: function(error) {
            console.log(error)
        }
    })
}