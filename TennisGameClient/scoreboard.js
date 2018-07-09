$(function () {

  // Time to visualize the message
  const showMessageSec = 3000;

  // Add function to click event buttons
  $('#point0').click(function (event) {
    event.stopImmediatePropagation();
    point(0);
  });

  $('#point1').click(function (event) {
    event.stopImmediatePropagation();
    point(1);
  });

  /**
   * Adds a point to player. Retrives and shows a message of action.
   * @param player
   */
  function point(player) {
    $.ajax({
      type: "put",
      url: "http://localhost:8080/point/" + player,
      crossDomain: true,
      cache: false,
      dataType: "json",
      contentType: "application/json; charset=UTF-8",
      // Needed to keep cookie session with CORS enabled
      xhrFields: {withCredentials: true},
      success: function (data) {
        $("#message").show();
        $("#message").html(data.msg);
        // Retrieve the score, which could be return here as well
        score();
      },
      error: function (xhr, textStatus, errorThrown) {
        console.error(xhr);
        console.error(textStatus);
        console.error(errorThrown);
      }
    });

  }

  /**
   * Retrieves the score for the match in session
   */
  function score() {
    $.ajax({
      type: "get",
      url: "http://localhost:8080/score",
      crossDomain: true,
      cache: false,
      dataType: "json",
      contentType: "application/json; charset=UTF-8",
      xhrFields: {withCredentials: true},
      success: function (data) {

        // Fade out the message after seconds
        setTimeout(function () {
          $("#message").fadeOut("slow");
        }, showMessageSec);

        // Fill score for players
        $("#p0Score").html(data.score.player0);
        $("#p1Score").html(data.score.player1);

        // Fill sets for players
        let divSet, jsonSet;
        for (let i = 1; i < 6; i++) {

          divSet = ("#p0Set" + i);
          jsonSet = data["sets"][i - 1].player0;
          $(divSet).html(jsonSet);

          divSet = ("#p1Set" + i);
          jsonSet = data["sets"][i - 1].player1;
          $(divSet).html(jsonSet);
        }
      },
      error: function (xhr, textStatus, errorThrown) {
        console.error(xhr);
        console.error(textStatus);
        console.error(errorThrown);
      }
    });
  }

});