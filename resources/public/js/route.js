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
        $("input[name=ship-name]").val(ui.item.label);
      }
    });

    $("#character-search").autocomplete({
      source: function(request, response) {
        $.ajax({
          url: "/character/search.json",
          data: {
            "term": request.term,
            "apiKeys[]": $.jStorage.get("apiKeys", []).map(function(x){return x.id + ":" + x.code})
          },
          success: function(data) {
            response(data);
          }
        });
      },
      minLength: 2,
      select: function(event, ui) {
        $("input[name=character-name]").val(ui.item.label);
        $("input[name=character]").val(ui.item.id);
        $("input[name=keyID]").val(ui.item.keyID);
        $("input[name=vCode]").val(ui.item.vCode);
      }
    });

    $("input[type=submit]").button();
  });
