/*
 * Function: searchfield
 * A jQuery plugin that renders and provide behavior for a search field.
 * As keys are pressed, the plugin returns the field's current value to
 * the provided callback routine (which would contain the actual filtering logic)
 *
 * Usage:
 * $(".scrollWithFade").fadeScroller();
 */

/*jslint browser: true */
/*global $, jQuery, console, mwf, define */

(function(global) {
  "use strict";

  var defaults = {
        showWorkingIndicator: false,
        workingIndicatorDelay: 250
      },

      pluginName = "mwf_plugin_overlay";


    // The actual plugin constructor
    var Overlay = function(element, options) {
      this.element = element;
      this.settings = $.extend({}, defaults, options);
      this.init();
    };

    Overlay.prototype = {
      init: function() {
        var self = this,
          options = self.settings;
        
        self.overlay = $("<div>")
          .addClass("mwfOverlay")
          .appendTo(self.element);

        if(options.showWorkingIndicator) {
          self.spinner = $("<div>")
          .addClass("centered")
          .attr("data-working-indicator","")
          .appendTo(self.overlay);
          self.overlay.mwfWorkingIndicator({ delay: options.workingIndicatorDelay });
        }  


      },

      remove: function() {
        var self = this;
        self.overlay.mwfWorkingIndicator("stop");
        self.overlay.remove();
      }

    };

    $.fn.mwfOverlay = function(options) {
      return this.each(function() {
        if (!$.data(this, pluginName)) {
          $.data(this, pluginName, new Overlay(this, options));
        } else {
          switch (options) {
            case "remove":
              $.data(this, pluginName).remove();
              $.removeData(this, pluginName);
              break;
            default:
              break;
          }
        }
      });
    };

 
})(this);
