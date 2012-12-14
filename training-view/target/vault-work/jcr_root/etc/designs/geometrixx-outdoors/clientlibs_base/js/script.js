/*

  ADOBE CONFIDENTIAL
  __________________

   Copyright 2011 Adobe Systems Incorporated
   All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
*/

// Placeholder compatibility script
jQuery(function ($) {

var supportsPlaceholder = "placeholder" in document.createElement("input");

$("input[placeholder]").each(function () {
    var input = $(this);
    
    if (!supportsPlaceholder) {
        var placeholder = input.attr("placeholder");
        
        if (!input.val()) {
            input.val(placeholder);
        }
        
        input
            .removeAttr("placeholder")
            .focus(function () {
                if (input.val() == placeholder) {
                    input.val("");
                }
            })
            .blur(function () {
                if (!input.val()) {
                    input.val(placeholder);
                }
            });
    }
});

});
