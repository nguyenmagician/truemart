$(document).ready(function() {
    $("#form").validate({
        rules: {
            "firstname": {
                required: true,
                minlength: 4
            },
            "lastname": {
                required: true,
                minlength: 4
            },
            "email": {
                required: true,
                email: true,
                remote: {
                    url: location.href.substring(0,location.href.lastIndexOf('/'))+"/checkemail",
                    type: "get",
                }
            },
            "telephone": {
                required: true,
            },
            "password": {
                required: true,
                minlength: 8
            },
            "repassword": {
                required: true,
                equalTo: "#password",
            },
            "agree": {
                required: true
            }
        },
        messages: {
            "firstname": {
                required: "Bắt buộc nhập firstname",
                maxlength: "Hãy nhập tối thiểu 4 ký tự"
            },
            "lastname": {
                required: "Bắt buộc nhập lastname",
                maxlength: "Hãy nhập tối thiểu 4 ký tự"
            },
            "email": {
                required: "Bắt buộc nhập email",
                email: "Hãy nhập một email hợp lệ",
                remote: "Email đã được sử dụng"
            },
            "telephone": {
                required: "Bắt buộc nhập telephone"
            },
            "password": {
                required: "Bắt buộc nhập password",
            },
            "repassword": {
                required: "Bắt buộc nhập re-password",
                equalTo: "Hai password phải giống nhau",
            },
            "agree": {
                required: "   Bắt buộc đồng ý"
            }
        }
    });
});