import styled, { keyframes } from "styled-components";

export const SkeletonProfileContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-evenly;
  width: 100%;
  background-color: #eef1f6;
  text-align: center;
  border-radius: 12px;
  padding: 10px;
  box-sizing: border-box;
  margin: 0 auto;
  @media (max-width: 1225px) {
    flex-direction: column;
  }
`;

export const SkeletonContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 97%;
  max-width: 1200px;
  background-color: #eef1f6;
  border-radius: 12px;
  padding: 10px;
  box-sizing: border-box;
`;

const skeletonAnimation = keyframes`
  0% {
    background-color: #f0f0f0;
  }
  50% {
    background-color: #e0e0e0;
  }
  100% {
    background-color: #f0f0f0;
  }
`;

export const SkeletonProfile = styled.div`
  width: 80%;
  height: 150px;
  border-radius: 12px;
  animation: ${skeletonAnimation} 1.5s infinite ease-in-out;
  margin-bottom: 20px;
`;

export const SkeletonSpecification = styled.div`
  width: 37%;
  height: 250px;
  border-radius: 8px;
  animation: ${skeletonAnimation} 1.5s infinite ease-in-out;
  margin-bottom: 10px;
  gap: 10px;
  &:nth-child(2n) {
    margin-right: 0;
  }
  @media (max-width: 1225px) {
    height: 150px;
    width: 80%;
  }
`;
