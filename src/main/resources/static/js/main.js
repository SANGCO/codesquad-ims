$(".milestones a").click(addMilestone);

function addMilestone(e) {
	console.log("여기 까지 들어오나?");
}


$(".new-comment button").click(addNewComment);

function addNewComment(e) {
	e.preventDefault();
	
	var queryString = $(".reply").val();
	console.log("comment : " + queryString);

	var url = $(".comment-write").attr("action");
	console.log("url : " + url);
	
	$.
}
