import React, { useEffect, useRef, useState } from 'react';
import * as d3 from 'd3';
import axios from 'axios';
import { Card, Icon } from "@mui/material";
import VuiBox from "components/VuiBox";
import VuiTypography from "components/VuiTypography";
import gif from "assets/images/cardimgfree.png";
import earth from "assets/images/earth.png";

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
    axios.get(" http://localhost:8080/dashboard/v1/read/neo-feed", {
    }).then(response => {
      const neos = Object.values(response.data.response).flat();
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
        
      const imageURL = earth; // 지구 이미지의 URL을 지정하세요.
      svg.append("image")
        .attr("xlink:href", imageURL)
        .attr("x", centerX - 70) // 원의 중심에서 이미지의 크기를 빼줍니다.
        .attr("y", centerY - 50) // 원의 중심에서 이미지의 크기를 빼줍니다.
        .attr("width", 120)  // 이미지의 크기
        .attr("height", 150); // 이미지의 크기

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
    <VuiBox height="100%" display="flex" flexDirection="column" justifyContent="space-between">
    <svg ref={svgRef} width={svgWidth} height={svgHeight}></svg>
    <VuiTypography color="text" variant="button" fontWeight="regular" mb="12px">
      <button onClick={() => setShouldRestart(!shouldRestart)}>Restart Animation</button>
    </VuiTypography>
  </VuiBox>
  );
};

export default Dashboard ;
