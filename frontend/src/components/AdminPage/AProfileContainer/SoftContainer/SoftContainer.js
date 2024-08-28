import React, { useState, useEffect, useRef, useCallback } from "react";
import styled from "styled-components";
import getIconForMark from "../../../common/IconsForMarks/IconsForMarks";
import LegendInfo from "../../../common/LegendInfo/LegendInfo";

import ArrowIconUp from "../../../assets/images/ArrowUp.png";
import ArrowIcon from "../../../assets/images/ArrowDown.png";
import LoadingAnim from "../../../assets/images/LoadingAnim.gif";
import { getSoftInformation } from "../../../../../api/GetPostResponses";

import {
  ModalOverlay2,
  ModalContent,
  CloseButton,
  ModalHeader,
  ModalInputTextHeader,
  Select,
  SaveButton,
} from "../../../common/ModalWindow/ModalWindow";
import { updateSoftSkill } from "../../../../../api/GetPostResponses";

const SoftBlock = styled.div`
  background-color: #eef1f6;
  border-radius: 12px;
  padding: 10px;
  border: 6px solid white;
  margin: 10px auto;
  word-wrap: break-word;
`;

const SoftMainHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  border-radius: 12px;
  cursor: pointer;
  font-family: "Inter", sans-serif;
  font-weight: 600;
  font-size: 24px;
  color: #333;
`;

const SoftContent = styled.div`
  width: 100%;
  overflow: hidden;
  max-height: ${(props) => (props.$show ? `${props.height}px` : "0")};
  opacity: ${(props) => (props.$show ? "1" : "0")};
  transition: max-height 0.7s ease-in-out, opacity 0.7s ease-in-out;
`;

const SArrowImage = styled.img`
  width: 72px;
  height: 72px;
`;

const SHardListText = styled.span`
  margin-left: 10px;
`;

const ProgressContainer = styled.div`
  background-color: #dfe3e9;
  height: 16px;
  width: calc(100% - 20px);
`;

const ProgressBar = styled.div`
  background-color: #6ec4f8;
  width: ${(props) => props.$progress}%;
  height: 100%;
`;

const SkillBlock = styled.div`
  margin-top: 20px;
  padding-left: 20px;
`;

const SkillHeader = styled.div`
  font-size: 24px;
  margin-bottom: 15px;
  color: #c2c8d2;
  font-family: "Inter", sans-serif;
`;

const Row = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 20px;
  gap: 5px;
`;

const RowLower = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  gap: 20px;
  margin-bottom: 10px;
  font-family: "Inter", sans-serif;
  font-weight: 500;

  @media (max-width: 650px) {
    font-size: 20px;
  }
  @media (max-width: 430px) {
    font-size: 16px;
  }
`;

const SkillItem = styled.div`
  display: flex;
  align-items: center;
  font-size: 18px;
  margin-bottom: 10px;
  font-family: "Inter", sans-serif;
  &::before {
    content: "${(props) => props.icon}";
    font-size: ${(props) => (props.icon === "❗" ? "18px" : "24px")};
    margin-right: 10px;
  }
`;

const SoftElem = styled.div`
  margin-top: 20px;
  background-color: #fff;
  border-radius: 12px;
  margin-bottom: 30px;
  padding: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
`;

const SkiilSwitch = styled.div`
  font-size: 20px;
  font-family: "Inter", sans-serif;
  font-weight: 100;
  color: #000000;
  text-align: right;
  cursor: pointer;
  margin-top: ${(props) => (props.$show ? "20px" : "10px")};
  transition: margin-top 0.3s ease-in-out;
  @media (max-width: 650px) {
    font-size: 18px;
  }
  @media (max-width: 430px) {
    font-size: 14px;
  }
`;

const Line = styled.div`
  text-align: center;
  width: 97%;
  height: 2px;
  background-color: #bdbac0;
  margin: 20px auto;
`;

const PercentShow = styled.div`
  background-color: #e6f4ea;
  border-radius: 12px;
  padding: 10px 20px;
  color: #0f9d58;
  font-size: 18px;
  font-family: "Inter", sans-serif;
  font-weight: 600;
`;

const NameSkill = styled.div`
  color: #000;
  margin-left: 28px;
  font-family: "Inter", sans-serif;
  font-weight: 400;
  font-size: 32px;
  text-align: left;
  word-wrap: break-word;

  @media (max-width: 650px) {
    font-size: 25px;
  }
  @media (max-width: 430px) {
    font-size: 19px;
  }
`;

const SoftContainer = () => {
  const [showContent, setShowContent] = useState(false);
  const [showMoreContents, setShowMoreContents] = useState([]);
  const [contentHeight, setContentHeight] = useState(0);
  const [softSkillsData, setSoftSkillsData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isEditModalVisible, setEditModalVisible] = useState(false);
  const [selectedEditSkill, setSelectedEditSkill] = useState(null);
  const [selectedEditLevel, setSelectedEditLevel] = useState(0);
  const [isEditLoading, setIsEditLoading] = useState(false);
  const [selectedEditSkillName, setSelectedEditSkillName] = useState("");

  const contentRef = useRef(null);

  const toggleEditModal = useCallback(() => {
    setEditModalVisible((prev) => !prev);
  }, []);

  const handleEditItemClick = useCallback(
    (skill) => {
      if (skill) {
        setSelectedEditSkill(skill);
        setSelectedEditSkillName(skill.skill_name);
        setSelectedEditLevel(skill.mark);
        toggleEditModal();
      }
    },
    [toggleEditModal]
  );

  const handleEditSave = useCallback(async () => {
    setIsEditLoading(true);

    try {
      const updatedSkills = softSkillsData.map((group) => {
        return {
          ...group,
          soft_skills: group.soft_skills.map((skill) => {
            if (skill.skill_id === selectedEditSkill.skill_id) {
              return {
                ...skill,
                mark: selectedEditLevel,
              };
            }
            return skill;
          }),
        };
      });

      await updateSoftSkill(1, selectedEditSkill.skill_id, selectedEditLevel);

      setSoftSkillsData(updatedSkills);
      toggleEditModal();
    } catch (error) {
      console.error("Error saving skill:", error);
    } finally {
      setIsEditLoading(false);
    }
  }, [softSkillsData, selectedEditSkill, selectedEditLevel, toggleEditModal]);

  const toggleMoreContent = useCallback((index) => {
    setShowMoreContents((prev) => {
      const newContents = [...prev];
      newContents[index] = !newContents[index];
      return newContents;
    });
  }, []);

  const toggleContent = useCallback(() => setShowContent((prev) => !prev), []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getSoftInformation(1);
        if (data && data.soft_marks) {
          setSoftSkillsData(data.soft_marks);
          setShowMoreContents(new Array(data.soft_marks.length).fill(false));
        } else {
          setSoftSkillsData([]);
          setShowMoreContents([]);
        }
      } catch (error) {
        console.error("Ошибка при получении данных софт-навыков:", error);
        setSoftSkillsData([]);
        setShowMoreContents([]);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    if (contentRef.current) {
      setContentHeight(contentRef.current.scrollHeight);
    }
  }, [showContent, showMoreContents]);

  return (
    <SoftBlock>
      <SoftMainHeader onClick={toggleContent}>
        <SHardListText>
          Карта софтовых компетенций с уровнем прогресса
        </SHardListText>
        <SArrowImage
          src={loading ? LoadingAnim : showContent ? ArrowIconUp : ArrowIcon}
          alt="Toggle"
        />
      </SoftMainHeader>
      {!loading && (
        <SoftContent
          $show={showContent}
          height={contentHeight}
          ref={contentRef}
        >
          {softSkillsData.map((group, index) => {
            const totalMarks = group.soft_skills.reduce(
              (sum, skill) => sum + skill.mark,
              0
            );
            const maxMarks = group.soft_skills.length * 3;
            const percentage =
              maxMarks > 0 ? Math.round((totalMarks / maxMarks) * 100) : 0;

            return (
              <SoftElem key={index}>
                <SkillHeader>
                  <Row>
                    <PercentShow>{percentage}%</PercentShow>
                    <NameSkill>{group.soft_group_name}</NameSkill>
                  </Row>
                  <RowLower>
                    <span>Прогресс</span>
                    <ProgressContainer>
                      <ProgressBar $progress={percentage} />
                    </ProgressContainer>
                  </RowLower>
                </SkillHeader>
                {showMoreContents[index] && (
                  <SkillBlock>
                    <Line />
                    {group.soft_skills.map((skill) => (
                      <SkillItem
                        key={skill.skill_id}
                        icon={getIconForMark(skill.mark)}
                        onClick={() => handleEditItemClick(skill)}
                        style={{ cursor: "pointer" }}
                      >
                        {skill.skill_name}
                      </SkillItem>
                    ))}

                    <Line />
                    <LegendInfo showCountLegend={5} />
                    <SkiilSwitch
                      $show={showMoreContents[index]}
                      onClick={() => toggleMoreContent(index)}
                    >
                      Скрыть ↑
                    </SkiilSwitch>
                  </SkillBlock>
                )}
                {!showMoreContents[index] && (
                  <SkiilSwitch
                    $show={showMoreContents[index]}
                    onClick={() => toggleMoreContent(index)}
                  >
                    Подробнее ↓
                  </SkiilSwitch>
                )}
              </SoftElem>
            );
          })}
          <ModalOverlay2
            $isVisible={isEditModalVisible}
            onClick={toggleEditModal}
          >
            <ModalContent onClick={(e) => e.stopPropagation()}>
              <CloseButton onClick={toggleEditModal}>&times;</CloseButton>
              {selectedEditSkill && (
                <>
                  <ModalHeader>Компетенция</ModalHeader>
                  <ModalInputTextHeader>
                    Изменить уровень компетенции:
                  </ModalInputTextHeader>
                  <Select
                    value={selectedEditLevel}
                    onChange={(e) =>
                      setSelectedEditLevel(Number(e.target.value))
                    }
                  >
                    {[-1, 0, 1, 2, 3].map((level) => (
                      <option key={level} value={level}>
                        {level}
                      </option>
                    ))}
                  </Select>
                  <SaveButton onClick={handleEditSave}>
                    {isEditLoading ? "Сохранение..." : "Сохранить"}
                  </SaveButton>
                </>
              )}
            </ModalContent>
          </ModalOverlay2>
        </SoftContent>
      )}
    </SoftBlock>
  );
};

export default SoftContainer;
