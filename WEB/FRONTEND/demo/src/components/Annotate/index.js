import React, { Component } from "react";
import styled from "styled-components";
import Multiple from "../Samples/Multiple";

const Container = styled.main`
  margin: 0 auto;
  padding-top: 16px;
  padding-bottom: 64px;
  max-width: 700px;
`;

export default class Annotate extends Component {
  render() {
    return (
      <Container>
        <Multiple />
      </Container>
    );
  }
}
