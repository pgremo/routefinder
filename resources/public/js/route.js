$(function() {
    function log(message) {
      var html = '<li><input type="hidden" id="waypoint-{0}" name="waypoint[]" value="{0}" /><span class="waypoint-label">{0}</span><a class="waypoint-delete" href="#">X</a></li>';
      $("#waypoints").append(html.format(message));
    }

    $("#waypoints").on("click", ".waypoint-delete", function(){
      $(this).closest("li").remove();
      return false;
    });

    $("#solarsystem").autocomplete({
      source: "/solarsystem/search.json",
      minLength: 2,
      select: function(event, ui) {
        if (ui.item) log(ui.item.value)
      }
    });

    $("input[type=submit]").button();
  });
