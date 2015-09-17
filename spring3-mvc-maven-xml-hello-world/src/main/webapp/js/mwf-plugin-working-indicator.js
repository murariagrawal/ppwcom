/*
 *
 * Copyright (c) 2013 Wells Fargo. 45 Fremont St., San Francisco, CA 94105 All
 * rights reserved.
 *
 * This software is the confidential and proprietary information of Wells Fargo
 * bank. ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with WellsFargo.
 */

/*
 * Author: Barry, Thomas
 * File description: render a loading indicator that responds to events
 */

/*jslint browser: true */
/*global jQuery */

(function (global) {
  "use strict";

  var defaults = {
        delay: 1000
      },

      pluginName = "mwfPluginIndicator";

 

    // Constructor
    var WorkingIndicator = function (element, options) {
      this.element = $(element);
      this.settings = $.extend({}, defaults, options);
      this.wrappers = [];
      this.init();
    };

    // Define common methods
    WorkingIndicator.prototype = {

      init: function () {
        var self = this;
        self.element.find("[data-working-indicator]").each(function () {
          $(this).addClass("mwfWorkingIndicatorWrapper");
          $("<div>")
              .addClass("mwfSpinner mwfIconSpinner")
              .attr("aria-hidden", "true")
              .appendTo($(this));
          self.wrappers.push($(this));
        });
        self.start();
      },

      start: function () {
        var self = this;
        $.each(self.wrappers, function (index, indicatorWrapper) {
          indicatorWrapper.show();
          indicatorWrapper.children().hide();
          setTimeout(function () {
            indicatorWrapper.children().fadeIn();
          }, self.settings.delay);
        });
      },

      stop: function () {
        var self = this;
        $.each(self.wrappers, function (index, indicatorWrapper) {
          indicatorWrapper.empty().show();
        });
      }

    };

    // Define the jQuery plugin.
    $.fn.mwfWorkingIndicator = function (options) {
      return this.each(function () {
        if (!$.data(this, pluginName)) {
          $.data(this, pluginName, new WorkingIndicator(this, options));
        } else {
          switch (options) {
            case "stop":
              $.data(this, pluginName).stop();
              $.removeData(this, pluginName);
              break;
            case "remove":
              $.data(this, pluginName).stop();
              $(this).find("[data-working-indicator]").remove();
              break;
            default:
              break;
          }
        }
      });
    };

})(this);
