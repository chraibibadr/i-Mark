import styled, { css } from "styled-components";
import { Link } from "react-router-dom";

const styles = css`
  background: #c26a0c;
  border: 0;
  border-radius: 5px
  color: white;
  cursor: pointer;
  font-family: Montserrat;
  font-size: 13px;
  font-weight: 700;
  outline: 0;
  margin: 4px;
  padding: 8px 16px;
  text-shadow: 0 1px 0 rgba(0, 0, 0, 0.1);
  text-transform: uppercase;

  transition: background 0.21s ease-in-out;
  transition: border-radius 0.21s ease-in-out;

  &:focus,
  &:hover {
    background: #a35a0c;
    border-radius: 10px;
  }
`;

export default styled.button`
  ${(props) => styles}
`;

export const ButtonLink = styled(Link)`
  text-decoration: none;
  ${(props) => styles}
`;
