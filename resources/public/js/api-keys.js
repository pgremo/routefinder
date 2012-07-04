$(function(){
  var addKey = function(node, entry){
    var html = '<li class="ui-state-default"><input type="hidden" id="{0}" name="keyID[]" value="{1}" /><span class="hint ui-icon ui-icon-arrowthick-2-n-s"></span>{0}:{1}<span class="delete ui-icon ui-icon-closethick"></span></li>';
    node.append(html.format(entry.id, entry.code));
  }

  $.jStorage.get("apiKeys", []).reduce(addKey, $("#keys"));

  $("#add").click(function(){
    addKey($("#keys"), {id: this.form.keyID.value, code: this.form.vCode.value});
    return false;
  });
  $("#save").click(function(){
    var values = $("input[name='keyID[]']").get().map(function(node){
                                                            return {id: node.id, code: node.value};
                                                          })
    $.jStorage.set("apiKeys", values);
    return false;
  });

  $("#keys").on("click", ".delete", function(){
    $(this).closest("li").remove();
    return false;
  });
})