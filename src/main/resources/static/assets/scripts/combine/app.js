$(function () {
   var token = $("meta[name='_csrf']").attr("content");
   var headerProp = $("meta[name='_csrf_header']").attr("content");
   var o = {};
   o[headerProp] = token;
   $( "#contact" ).click(function( event ) {
      var datao = {"email": $( "#cu-email" ).val(), "contents": $( "#cu-message" ).val(), "url": $(location).attr('href') };
      if (datao.contents) {
         $.ajax({
             url: '/contactus',
             method: "POST",
               headers: o,
               data: JSON.stringify(datao),
               processData: false,
               contentType: 'application/json',
             success: function (data) {
               $( "#cu-form" ).replaceWith( "<p>Thank you!</p>" );
             }
         });
      }

   });
});
