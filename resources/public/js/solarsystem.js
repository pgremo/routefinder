$(function() {
		function log( message ) {
            var html = '<input type="checkbox" checked="checked" id="waypoint-'+message+'" name="waypoint" value="'+message+'" /> <label for="waypoint-'+message+'">'+message+'</label>';
//			$( "<div/>" ).text( message ).appendTo( "#log" );
            $( "#log" ).append(html);
//			$( "#log" ).scrollTop( 0 );
		}

		$( "#solarsystem" ).autocomplete({
			source: "search.json",
			minLength: 2,
			select: function( event, ui ) {
				log( ui.item ?
					ui.item.value :
					"Nothing selected, input was " + this.value );
			}
		});
	});