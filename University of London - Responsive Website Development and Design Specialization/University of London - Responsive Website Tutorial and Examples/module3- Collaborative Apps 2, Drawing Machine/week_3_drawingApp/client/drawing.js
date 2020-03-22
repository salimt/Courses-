Canvas = function () {
  var self = this;
  var svg;

  var createSvg = function() {
    svg = d3.select('#canvas').append('svg')
      .attr('width', 900)
      .attr('height',600);
  };
  createSvg();

  self.clear = function() {
    d3.select('svg').remove();
    createSvg();
  };

  self.draw = function(data) {
    if (data.length < 1) {
      self.clear();
      return;
    }
    if (svg) {


 // /* For the drop shadow filter... */
 //  var defs = svg.append("defs");

 //  var filter = defs.append("filter")
 //      .attr("id", "dropshadow");

 //  filter.append("feGaussianBlur")
 //      .attr("in", "SourceAlpha")
 //      .attr("stdDeviation", 4)
 //      .attr("result", "blur");
 //  filter.append("feOffset")
 //      .attr("in", "blur")
 //      .attr("dx", 2)
 //      .attr("dy", 2)
 //      .attr("result", "offsetBlur");

 //  var feMerge = filter.append("feMerge");

 //  feMerge.append("feMergeNode")
 //      .attr("in", "offsetBlur")
 //  feMerge.append("feMergeNode")
 //      .attr("in", "SourceGraphic");





        // Remember to format the data properly in markPoints

        // to draw a circle - 
        // svg.selectAll('circle').data(data, function(d) { return d._id; })
        // .enter().append('circle')
        // .attr('r', 10)
        // .attr('cx', function (d) { return d.x; })
        // .attr('cy', function (d) { return d.y; });

      //to draw a line
      svg.selectAll('line').data(data, function(d) { return d._id; })
      .enter().append('line')
      .attr('x1', function (d) { return d.x; })
      .attr('y1', function (d) { return d.y; })
      .attr('x2', function (d) { return d.x1; })
      .attr('y2', function (d) { return d.y1; })
      .attr("stroke-width", function (d) { return d.w; })
      .attr("stroke", function (d) { return d.c; })
      .attr("stroke-linejoin", "round");


    } // end of the if(svg) statement
  }; // end of the canvas.draw function
} //end of the canvas function

