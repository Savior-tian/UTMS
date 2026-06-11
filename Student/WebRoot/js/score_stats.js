function drawBarChart(buckets) {
	var canvas = document.getElementById("scoreChart");
	if (!canvas || !canvas.getContext) {
		return;
	}
	var ctx = canvas.getContext("2d");
	var width = canvas.width;
	var height = canvas.height;
	ctx.clearRect(0, 0, width, height);

	var labels = ["<60", "60-69", "70-79", "80-89", ">=90"];
	var values = [
		buckets.lt60 || 0,
		buckets.s60_69 || 0,
		buckets.s70_79 || 0,
		buckets.s80_89 || 0,
		buckets.gte90 || 0
	];
	var maxVal = Math.max.apply(null, values);
	if (maxVal < 1) {
		maxVal = 1;
	}

	var padding = 45;
	var chartW = width - padding * 2;
	var chartH = height - padding * 2;
	var barW = 90;
	var gap = (chartW - barW * values.length) / (values.length - 1);

	ctx.strokeStyle = "#9ab4cb";
	ctx.lineWidth = 1;
	ctx.beginPath();
	ctx.moveTo(padding, padding);
	ctx.lineTo(padding, height - padding);
	ctx.lineTo(width - padding, height - padding);
	ctx.stroke();

	ctx.fillStyle = "#5a7f9e";
	ctx.font = "12px Microsoft YaHei";
	for (var i = 0; i <= 5; i++) {
		var y = height - padding - chartH * i / 5;
		var scale = Math.round(maxVal * i / 5);
		ctx.fillText(scale, 12, y + 4);
		ctx.strokeStyle = "#edf4fb";
		ctx.beginPath();
		ctx.moveTo(padding, y);
		ctx.lineTo(width - padding, y);
		ctx.stroke();
	}

	for (var j = 0; j < values.length; j++) {
		var barH = chartH * values[j] / maxVal;
		var x = padding + j * (barW + gap);
		var y0 = height - padding - barH;
		var grd = ctx.createLinearGradient(x, y0, x, height - padding);
		grd.addColorStop(0, "#1f78bc");
		grd.addColorStop(1, "#2bb1a0");
		ctx.fillStyle = grd;
		ctx.fillRect(x, y0, barW, barH);

		ctx.fillStyle = "#1c4767";
		ctx.fillText(values[j], x + barW / 2 - 8, y0 - 8);
		ctx.fillStyle = "#38566f";
		ctx.fillText(labels[j], x + barW / 2 - 16, height - padding + 18);
	}
}

function renderSubjectTable(subjects) {
	var $table = $("#subjectTable");
	$table.find("tr:gt(0)").remove();
	if (!subjects || subjects.length === 0) {
		$table.append("<tr><td colspan='5'>暂无统计数据</td></tr>");
		return;
	}
	for (var i = 0; i < subjects.length; i++) {
		var item = subjects[i];
		var row = "<tr>"
			+ "<td>" + item.subject + "</td>"
			+ "<td>" + item.avgScore + "</td>"
			+ "<td>" + item.passCount + "</td>"
			+ "<td>" + item.failCount + "</td>"
			+ "<td>" + item.totalCount + "</td>"
			+ "</tr>";
		$table.append(row);
	}
}

$(function() {
	$.post("/Student/ScoreStatsServlet", null, function(rs) {
		if (rs.error) {
			alert(rs.error);
			return;
		}
		$("#scope").text("数据范围：" + rs.scope);
		$("#avgScore").text(rs.overall.avgScore);
		$("#passCount").text(rs.overall.passCount);
		$("#failCount").text(rs.overall.failCount);
		$("#passRate").text(rs.overall.passRate + "%");
		drawBarChart(rs.buckets);
		renderSubjectTable(rs.subjects);
	}, "json");
});
