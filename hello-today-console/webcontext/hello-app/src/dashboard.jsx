import React, { useEffect, useRef, useState } from 'react';
import * as d3 from 'd3';
import axios from 'axios';

const Dashboard = () => {
  const [neoData, setNeoData] = useState(null);
  const [shouldRestart, setShouldRestart] = useState(false);
  const svgRef = useRef();
  const svgWidth = 700;  // SVG의 너비
  const svgHeight = 700;  // SVG의 높이

  const centerX = svgWidth / 2;  // 중심 x 좌표
  const centerY = svgHeight / 2;  // 중심 y 좌표

  useEffect(() => {
    // console 호출
    axios.get("https://localhost:8080", {
    }).then(response => {
      const neos = Object.values(response.data.near_earth_objects).flat();
      setNeoData(neos);
    });
  }, []);

  useEffect(() => {
    if (neoData) {
      // D3.js 코드
      const svg = d3.select(svgRef.current);
      svg.selectAll("*").remove();

      // 가상의 지구
      svg.append("circle")
        .attr("cx", centerX)
        .attr("cy", centerY)
        .attr("r", 50)
        .style("fill", "blue");

      // 운석
      svg.selectAll(".neo")
        .data(neoData)
        .enter()
        .append("circle")
        .attr("class", "neo")
        .attr("cx", (d, i) => centerX + Math.sin(i) * 100)
        .attr("cy", (d, i) => centerY + Math.cos(i) * 100)
        .attr("r", 5)
        .style("fill", "red")
        .transition()
        .duration(2000)
        .attr("cx", (d, i) => centerX + Math.sin(i) * (100 + d.estimated_diameter.meters.estimated_diameter_min))
        .attr("cy", (d, i) => centerY + Math.cos(i) * (100 + d.estimated_diameter.meters.estimated_diameter_min));
    }
  }, [neoData, shouldRestart]);

  return (
    <div>
      <h1>NEO Dashboard</h1>
      <button onClick={() => setShouldRestart(!shouldRestart)}>Restart Animation</button>
      <svg ref={svgRef} width={svgWidth} height={svgHeight}></svg>
    </div>
  );
};

export default Dashboard ;
