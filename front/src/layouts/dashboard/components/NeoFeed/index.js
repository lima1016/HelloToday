import React, { useEffect, useRef, useState } from 'react';
import * as d3 from 'd3';
import axios from 'axios';
import { Card } from "@mui/material";
import VuiBox from "components/VuiBox";
import VuiTypography from "components/VuiTypography";
import gif from "assets/images/milky-way.jpg";
import earth from "assets/images/earth.png";
import meteor from "assets/images/meteor.png";

const Dashboard = () => {
  const [neoData, setNeoData] = useState(null);
  const [shouldRestart, setShouldRestart] = useState(false);
  const svgRef = useRef();
  const svgWidth = 600;  // SVG의 너비
  const svgHeight = 320;  // SVG의 높이
  const centerX = 400;  // 중심 x 좌표를 SVG 너비의 절반으로 설정
  const centerY = svgHeight/2;  // 중심 y 좌표를 SVG 높이의 절반으로 설정
  

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
      const imageURL = earth; // 지구 이미지의 URL을 지정하세요.
      svg.append("image")
        .attr("xlink:href", imageURL)
        .attr("x", centerX - 70) // 원의 중심에서 이미지의 크기를 빼줍니다.
        .attr("y", centerY - 70) // 원의 중심에서 이미지의 크기를 빼줍니다.
        .attr("width", 140)  // 이미지의 크기
        .attr("height", 150); // 이미지의 크기

      // 운석
      const meteorImageUrl = meteor;
      // 패턴을 SVG에 추가합니다.
      svg.append("defs")
        .append("pattern")
        .attr("id", "meteorPattern")
        .attr("width", 5)
        .attr("height", 5)
        .append("image")
        .attr("xlink:href", meteorImageUrl)
        .attr("width", 15)
        .attr("height", 15);

      // 원을 그립니다.
      svg.selectAll(".neo")
        .data(neoData)
        .enter()
        .append("circle")
        .attr("class", "neo")
        .attr("cx", (d, i) => centerX + Math.sin(i) * 100)
        .attr("cy", (d, i) => centerY + Math.cos(i) * 100)
        .attr("r", 5)
        .style("fill", "url(#meteorPattern)") // 이 부분이 변경된 부분입니다.
        .transition()
        .duration(2000)
        .attr("cx", (d, i) => centerX + Math.sin(i) * (100 + d.estimated_diameter.meters.estimated_diameter_min))
        .attr("cy", (d, i) => centerY + Math.cos(i) * (100 + d.estimated_diameter.meters.estimated_diameter_min));

    }
  }, [neoData, shouldRestart]);

  return (
    <Card sx={() => ({
      height: "320px",
      backgroundImage: `url(${gif})`,
      backgroundSize: "cover",
      backgroundPosition: "50%"
    })}>
      <VuiBox height="100%" display="flex" flexDirection="column" justifyContent="space-between">
        <VuiBox>
          <svg ref={svgRef} width={svgWidth} height={svgHeight}></svg>
        </VuiBox>
      </VuiBox>
      <VuiTypography color="text" variant="button" fontWeight="regular" mb="12px">
            <button onClick={() => setShouldRestart(!shouldRestart)}>Restart Animation</button>
      </VuiTypography>
    </Card>
  );
};

export default Dashboard ;
