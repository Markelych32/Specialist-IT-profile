import React, { useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const ButtonGroup = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  width: 100%;
`;

const Button = styled.button`
  background-color: ${(props) => (props.$active ? "#49B0FA" : "#999999")};
  color: white;
  font-weight: 600;
  border-radius: 12px;
  border: none;
  padding: 10px 20px;
  font-size: 18px;
  margin: 10px;
  cursor: ${(props) => (props.$active ? "pointer" : "default")};
  opacity: ${(props) => (props.$inactive ? 0.6 : 1)};
  pointer-events: ${(props) => (props.$inactive ? "none" : "auto")};
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 200px;

  &:hover,
  &:focus {
    background-color: ${(props) => (props.$active ? "#007BFF" : "#888888")};
  }

  &:active {
    background-color: ${(props) => (props.$active ? "#007BFF" : "#666666")};
  }
`;

const ButtonPanel = () => {
  const [nonActive, setNonActive] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleNavigation = (path) => {
    setIsLoading(true);
    navigate(path);
  };

  return (
    <ButtonGroup>
      <Button
        $inactive={nonActive}
        onClick={() => handleNavigation("/")}
        $active={!isLoading}
      >
        {isLoading ? "Загрузка..." : "Пользователь"}
      </Button>
      <Button
        $inactive={isLoading}
        onClick={() => handleNavigation("/admin")}
        $active={!isLoading}
      >
        {isLoading ? "Загрузка..." : "Админ"}
      </Button>
    </ButtonGroup>
  );
};

export default ButtonPanel;
