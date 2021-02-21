// $(document).ready(function() {
//     $("a.AC").click(function(event) {
//         let parent = $(event.target).parent("div");
//
//         let product_id = parent.children("AC_product_id").val();
//         let type = parent.children(".AC_xacdinh").val();
//         let value = parent.children(".AC_xacdinh_value").val();
//
//         let url = location.href.substring(0,location.href.lastIndexOf('/')) + "/incart";
//
//         $.post(url,{
//             productId : product_id,
//             type: type,
//             value: value,
//             option: ""
//         },function (data,status) {
//             //alert("Data: " + data + "\nStatus: " + status);
//         })
//
//
//     });
// });