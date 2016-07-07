$(document).ready(function() {
		$(document).delegate('.open', 'click', function(event){
			$(this).addClass('oppenned');
			event.stopPropagation();
		})
		$(document).delegate('body', 'click', function(event) {
			$('.open').removeClass('oppenned');
		})
		$(document).delegate('.cls', 'click', function(event){
			$('.open').removeClass('oppenned');
			event.stopPropagation();
		});
                
                
                 $(".equipe1").click(function () {
            
                    var id = $(this).data("equipe");
                    var DATA = 'equipe=' + id ;

                    $.ajax({
                        type: "POST",
                        url: "",
                        data: DATA,
                        cache: false,
                        success: function (data) {
                                if(data.code == 0){
                                        $(".pari").empty();
                                        $(this).add("<div class='pari'>Votre parie a bien était pris en compte</div>");
                                }
                        }
                    });
                    return false;
                });
                
                
                $(".equipe2").click(function () {
            
                    var id = $(this).data("equipe");
                    var DATA = 'equipe=' + id ;
                   

                    $.ajax({
                        type: "POST",
                        url: "",
                        data: DATA,
                        cache: false,
                        success: function (data) {
                                if(data.code == 0){
                                        $(".pari").empty();
                                        $(this).add("<div class='pari'>Votre parie a bien était pris en compte</div>");
                                }
                        }
                    });
                    return false;
                });
                
                $(document).mouseup(function(e){
                    if (!  $('.register_form').is(e.target) && $('.register_form').has(e.target).length === 0) // ... nor a descendant of the container
                        {
                            $('.register_form').hide();
                        }
                        
                    if (!  $('.login_form').is(e.target) && $('.login_form').has(e.target).length === 0) // ... nor a descendant of the container
                        {
                            $('.login_form').hide();
                        }
                });
                
                $("#inscrip").click(function () {
            
                    $('.register_form').show();
                });
                
                
                $("#connec").click(function (event) {
                         
                    event.stopPropagation();
                    $('.login_form').toggle();
                });
                
                
          
	});