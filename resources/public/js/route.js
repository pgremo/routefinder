$(function() {
    $("#waypoints").on("click", ".waypoint-delete", function(){
      $(this).closest("li").remove();
      return false;
    });
    $("#waypoints").sortable().disableSelection();

    $("#solarsystem").autocomplete({
      source: "/solarsystem/search.json",
      minLength: 2,
      select: function(event, ui) {
        if (ui.item){
          var html = '<li class="ui-state-default"><input type="hidden" id="waypoint-{0}" name="waypoint[]" value="{0}" /><span class="ui-icon ui-icon-triangle-2-n-s"></span><span class="waypoint-label">{1}</span><span class="ui-icon ui-icon-closethick-2-n-s"></span></li>';
          $("#waypoints").append(html.format(ui.item.id, ui.item.label));
          $("#waypoints").sortable("refresh");
        }
      }
    });

    $("input[type=submit]").button();
  });
