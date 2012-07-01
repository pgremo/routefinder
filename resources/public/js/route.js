$(function() {
    $("#waypoints").on("click", ".delete", function(){
      $(this).closest("li").remove();
      return false;
    });
    $("#waypoints").sortable().disableSelection();

    $("#solarsystem").autocomplete({
      source: "/solarsystem/search.json",
      minLength: 2,
      select: function(event, ui) {
        if (ui.item){
          var html = '<li class="ui-state-default"><input type="hidden" id="waypoint-{0}" name="waypoint[]" value="{0}" /><span class="hint ui-icon ui-icon-arrowthick-2-n-s"></span>{1}<span class="delete ui-icon ui-icon-closethick"></span></li>';
          $("#waypoints").append(html.format(ui.item.id, ui.item.label));
          $("#waypoints").sortable("refresh");
        }
      }
    });

    $("#ship-search").autocomplete({
      source: "/ship/search.json",
      minLength: 2,
      select: function(event, ui) {
        $("input[name=ship]").val(ui.item.id);
        $("#ship-selected").val(ui.item.label)
      }
    });

    $("input[type=submit]").button();
  });
