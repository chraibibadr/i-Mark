import React, { Component } from 'react'
import styled from 'styled-components'
import Multi from '../Samples/Multiple'

const Container = styled.main`
  margin: 0 auto;
  padding-top: 16px;
  padding-bottom: 64px;
  max-width: 700px;
`

const SourceLink = styled.a`
  display: block;
  margin-top: 8px;
  font-size: 18px;
  text-align: center;
  text-decoration: none;
`

export default class Docs extends Component {
  render () {
    return (
      <Container>
        <Multi />
       
      </Container>
    )
  }
}
