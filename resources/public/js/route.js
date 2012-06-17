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
          var html = '<li><input type="hidden" id="waypoint-{0}" name="waypoint[]" value="{0}" /><span class="waypoint-label">{0}</span><a class="waypoint-delete" href="#">X</a></li>';
          $("#waypoints").append(html.format(ui.item.value));
          $("#waypoints").sortable("refresh");
        }
      }
    });

    $("input[type=submit]").button();
  });
